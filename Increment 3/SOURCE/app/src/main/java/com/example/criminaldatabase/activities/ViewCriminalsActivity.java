package com.example.criminaldatabase.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.criminaldatabase.R;
import com.example.criminaldatabase.model.Criminal;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.example.criminaldatabase.activities.PoliceUploadCriminalActivity.NO_DATA;

public class ViewCriminalsActivity extends AppCompatActivity {


    AlertDialog progressDialog;
    ArrayList<Criminal> criminalslist;
    private ListView listView1;
    CriminalAdapter adapter;
    private String currentAddress;
    int crim_id;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcriminal);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        criminalslist = new ArrayList<Criminal>();
        adapter = new CriminalAdapter(this,
                R.layout.listview_item_row, criminalslist);
        listView1 = (ListView) findViewById(R.id.listView1);

        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, listView1,false);
        // Add header view to the ListView
      //  listView1.addHeaderView(headerView);
        listView1.setAdapter(adapter);
        Location location=getLocation();
        if(location!=null){
            currentAddress=getAddressFromLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
        }else{
            currentAddress="";
        }

        new CriminalsApi(ViewCriminalsActivity.this).execute("");





    }

    private class CriminalsApi extends AsyncTask<String, Void, String> {
        Activity activity;

        public CriminalsApi(Activity activity) {
            this.activity = activity;
            progressDialog = new SpotsDialog(ViewCriminalsActivity.this, R.style.Custom);


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
                        if(currentAddress.equals("") ){
                            criminalslist.add(criminal);
                        }else if(obj.getString("crime_location").contains(currentAddress)){
                            criminalslist.add(criminal);
                        }


                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }


        }
    }

    private class ReportApi extends AsyncTask<String, Void, String> {
        Activity activity;

        public ReportApi(Activity activity) {
            this.activity = activity;
            progressDialog = new SpotsDialog(ViewCriminalsActivity.this, R.style.Custom);


        }

        protected void onPreExecute() {
            Log.d("Service Order", "Updating the service order");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String returnValue = "";
            syncReport();

            return returnValue;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            finish();


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
    public void syncReport() {
        String reportURL = "https://criminaldatabase.herokuapp.com/v1/reports";

        http://localhost:3000/v1/reports?user_id=10&address=aaaaaa&criminal_id=67
        Log.d("Service Order", "Uploading Report - Remote API " + reportURL);
        int responseCode = NO_DATA;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(reportURL);
            org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity();

            entity.addPart("user_id", new StringBody(""+prefs.getInt("user_id",0), "text/plain", Charset.forName("UTF-8")));
            entity.addPart("address", new StringBody(currentAddress, "text/plain", Charset.forName("UTF-8")));
            entity.addPart("criminal_id", new StringBody(""+crim_id, "text/plain", Charset.forName("UTF-8")));
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            String responseStr = EntityUtils.toString(response.getEntity());
            String str="";



        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    public class CriminalAdapter extends ArrayAdapter<Criminal> {

        Context context;
        int layoutResourceId;
        ArrayList<Criminal> list;

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
                holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
                holder.txtName = (TextView) row.findViewById(R.id.txtName);
                holder.txtRec_no = (TextView) row.findViewById(R.id.txtRec_no);
                holder.checkbox = (CheckBox) row.findViewById(R.id.checkbox);

                row.setTag(holder);
            } else {
                holder = (CriminalHolder) row.getTag();
            }

            Criminal criminal = list.get(position);
            holder.txtTitle.setText("" + criminal.getId());
            holder.txtName.setText("" + criminal.getCriminal_name());
            holder.txtRec_no.setText("" + criminal.getCriminal_rec_no());
            final CheckBox check=holder.checkbox;
            final int id=criminal.getId();
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(b){
                        crim_id=id;
                        displayDialog(check,id);
                    }
                }
            });

            return row;
        }

        class CriminalHolder {

            TextView txtTitle;
            TextView txtName;
            TextView txtRec_no;
            CheckBox checkbox;
        }
    }
    public Location getLocation() {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                return lastKnownLocationGPS;
            } else {
                return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        } else {
            return null;
        }

    }
    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(ViewCriminalsActivity.this);

        String address = "";
        try {

            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String address2 = addresses.get(0).getAddressLine(1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            if(postalCode!=null){
                address=postalCode;
            }else if(city!=null){
                address=city;
            }


        } catch (IOException e) {
        }

        return address;
    }

    public void displayDialog(final CheckBox view,final int criminal_id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewCriminalsActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm To Send Report...");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want report this criminal ?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
                new ReportApi(ViewCriminalsActivity.this).execute("");
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                view.setChecked(false);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
