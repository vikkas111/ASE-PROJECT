package com.example.criminaldatabase.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.criminaldatabase.R;
import com.example.criminaldatabase.model.Report;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

import static com.example.criminaldatabase.activities.PoliceUploadCriminalActivity.NO_DATA;

public class ViewReportsActivity extends AppCompatActivity {


    AlertDialog progressDialog;
    ArrayList<Report> reportslist;
    private ListView listView1;
    ReportAdapter adapter;
    int crim_id;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        reportslist = new ArrayList<Report>();
        adapter = new ReportAdapter(this,
                R.layout.report_listview_item_row, reportslist);
        listView1 = (ListView) findViewById(R.id.listView1);
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, listView1,false);
        listView1.setAdapter(adapter);
        new ReportsApi(ViewReportsActivity.this).execute("");
    }

    private class ReportsApi extends AsyncTask<String, Void, String> {
        Activity activity;

        public ReportsApi(Activity activity) {
            this.activity = activity;
            progressDialog = new SpotsDialog(ViewReportsActivity.this, R.style.Custom);
        }

        protected void onPreExecute() {
            Log.d("Service Order", "Updating the service order");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String returnValue = "";
            returnValue = getreports();
            return returnValue;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.length() > 0) {
                try {
                    JSONArray arry = new JSONArray(result);
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(i);
                        Report report = new Report();
                        report.setId(obj.getInt("id"));
                        if(obj.getString("mobile")!=null){
                            report.setMobile(obj.getString("mobile"));
                        }else{
                            report.setMobile("");
                        }
                        if(obj.getString("address")!=null){
                            report.setAddress(obj.getString("address"));
                        }else{
                            report.setAddress("");
                        }
                        if(obj.getString("user_id")!=null){
                            report.setUser_id(Integer.parseInt(obj.getString("user_id")));
                        }else{
                           // report.setUser_id();
                        }
                        if(obj.getString("criminal_id")!=null){
                            report.setCriminal_id(Integer.parseInt(obj.getString("criminal_id")));
                        }else{
                           // report.setCriminal_id("");
                        }
                        if(obj.getString("username")!=null){
                            report.setUsername(obj.getString("username"));
                        }else{
                             report.setUsername("");
                        }

                        JSONObject obj1 = obj.getJSONObject("criminal");
                        if(obj1.getString("criminalname")!=null){
                            report.setCriminalname(obj1.getString("criminalname"));
                        }else{
                            report.setCriminalname("");
                        }
                        if(obj1.getString("crime_location")!=null){
                            report.setCrime_location(obj1.getString("crime_location"));
                        }else{
                            report.setCrime_location("");
                        }
                        reportslist.add(report);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }


        }
    }

    public String getreports() {
        String criminalsURL = "https://criminaldatabase.herokuapp.com/v1/reports";
        Log.d("Service Order", "Uploading Image - Remote API " + criminalsURL);
        int responseCode = NO_DATA;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(criminalsURL);
            HttpResponse response = httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            String responseStr = EntityUtils.toString(response.getEntity());
            if (status == 200) {
                try {
                    return responseStr;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (status == 401) {
                Log.v("Unauthorized ", "" + responseStr);
            } else if (status == 422) {
                return "Email has already been taken";
            }


        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
        return "";

    }

    public class ReportAdapter extends ArrayAdapter<Report> {

        Context context;
        int layoutResourceId;
        ArrayList<Report> list;

        public ReportAdapter(Context context, int layoutResourceId, ArrayList<Report> list) {
            super(context, layoutResourceId, list);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            CriminalHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new CriminalHolder();
                holder.txtId = (TextView) row.findViewById(R.id.reportid);
                holder.txtReporter = (TextView) row.findViewById(R.id.reporter);
                holder.txtCriminalName = (TextView) row.findViewById(R.id.criminalName);
                holder.txtLoc = (TextView) row.findViewById(R.id.txtLoc);

                row.setTag(holder);
            } else {
                holder = (CriminalHolder) row.getTag();
            }

            Report report = list.get(position);
            holder.txtId.setText("" + report.getId());
            holder.txtCriminalName.setText("" + report.getCriminalname());
            holder.txtReporter.setText("" + report.getUsername());
            holder.txtLoc.setText("" + report.getCrime_location());

            return row;
        }

        class CriminalHolder {

            TextView txtReporter;
            TextView txtCriminalName;
            TextView txtLoc;
            TextView txtId;


        }
    }


}
