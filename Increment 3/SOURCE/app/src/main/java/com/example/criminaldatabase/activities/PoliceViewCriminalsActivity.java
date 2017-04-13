package com.example.criminaldatabase.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.criminaldatabase.R;
import com.example.criminaldatabase.model.Criminal;

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

public class PoliceViewCriminalsActivity extends AppCompatActivity {


    AlertDialog progressDialog;
    ArrayList<Criminal> criminalslist;
    ArrayList<Criminal> duplicatelist;
    private ListView listView1;
    CriminalAdapter adapter;
    int crim_id;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences prefs;
    private EditText inputSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_view_criminals);

        inputSearch=(EditText)findViewById(R.id.inputSearch);
        inputSearch.setVisibility(View.VISIBLE);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        criminalslist = new ArrayList<Criminal>();
        duplicatelist= new ArrayList<Criminal>();
        adapter = new CriminalAdapter(this,
                R.layout.police_listview_item_row, criminalslist);
        listView1 = (ListView) findViewById(R.id.listView1);

        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, listView1,false);
        // Add header view to the ListView
        //  listView1.addHeaderView(headerView);
        listView1.setAdapter(adapter);


        new CriminalsApi(PoliceViewCriminalsActivity.this).execute("");





    }

    private class CriminalsApi extends AsyncTask<String, Void, String> {
        Activity activity;

        public CriminalsApi(Activity activity) {
            this.activity = activity;
            progressDialog = new SpotsDialog(PoliceViewCriminalsActivity.this, R.style.Custom);


        }

        protected void onPreExecute() {
            Log.d("Service Order", "Updating the service order");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String returnValue = "";
            returnValue = getcriminals();

            return returnValue;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            if (result.length() > 0) {
                try {
                    JSONArray arry = new JSONArray(result);
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(i);
                        Criminal criminal = new Criminal();
                        criminal.setId(obj.getInt("id"));
                        criminal.setCriminal_name(obj.getString("criminalname"));
                        criminal.setEmail_id(obj.getString("email_id"));
                        criminal.setMobile(obj.getString("mobile"));
                        criminal.setAddress(obj.getString("address"));
                        criminal.setCriminal_rec_no(obj.getString("criminal_rec_no"));
                        criminal.setDescription(obj.getString("description"));
                        criminal.setCrime_location(obj.getString("crime_location"));
                        criminalslist.add(criminal);
                    }
                    duplicatelist=(ArrayList<Criminal>)criminalslist.clone();
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }


        }
    }

    public String getcriminals() {
        String criminalsURL = "https://criminaldatabase.herokuapp.com/criminals";
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

    public class CriminalAdapter extends ArrayAdapter<Criminal> {

        Context context;
        int layoutResourceId;
        ArrayList<Criminal> list;
        private CriminalFilter filter;

        public CriminalAdapter(Context context, int layoutResourceId, ArrayList<Criminal> list) {
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
                holder.txtRecord_no = (TextView) row.findViewById(R.id.record_no);
                holder.txtCriminalName = (TextView) row.findViewById(R.id.criminalName);
                holder.txtLoc = (TextView) row.findViewById(R.id.txtLoc);

                row.setTag(holder);
            } else {
                holder = (CriminalHolder) row.getTag();
            }

            Criminal criminal = list.get(position);
            holder.txtId.setText("" + criminal.getId());
            holder.txtCriminalName.setText("" + criminal.getCriminal_name());
            holder.txtRecord_no.setText("" + criminal.getCriminal_rec_no());
            holder.txtLoc.setText("" + criminal.getCrime_location());

            return row;
        }

        class CriminalHolder {

            TextView txtRecord_no;
            TextView txtCriminalName;
            TextView txtLoc;
            TextView txtId;

        }
        @Override
        public Filter getFilter() {
            if (filter == null){
                filter  = new CriminalFilter();
            }
            return filter;
        }
        private class CriminalFilter extends Filter
        {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if(constraint != null && constraint.toString().length() > 0)
                {
                    ArrayList<Criminal> filteredItems = new ArrayList<Criminal>();

                    criminalslist =(ArrayList<Criminal>)duplicatelist.clone();

                    for(int i = 0, l = criminalslist.size(); i < l; i++)
                    {
                        Criminal criminal = criminalslist.get(i);
                        if(criminal.getCrime_location().toString().toLowerCase().contains(constraint)){
                            filteredItems.add(criminal);
                        }

                        else if(criminal.getCriminal_name().toString().toLowerCase().contains(constraint)){
                            filteredItems.add(criminal);
                        }
                        else if(criminal.getCriminal_rec_no().toString().toLowerCase().contains(constraint)){
                            filteredItems.add(criminal);
                        }
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;
                }
                else
                {
                    synchronized(this)
                    {
                        result.values = criminalslist;
                        result.count = criminalslist.size();
                    }
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {

                criminalslist = (ArrayList<Criminal>)results.values;
                notifyDataSetChanged();
                clear();
                for(int i = 0, l = criminalslist.size(); i < l; i++)
                    add(criminalslist.get(i));
                notifyDataSetInvalidated();
            }
        }

    }


}
