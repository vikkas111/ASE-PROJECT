package com.example.criminaldatabase.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.criminaldatabase.R;
import com.example.criminaldatabase.database.DatabaseHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import dmax.dialog.SpotsDialog;

import static com.example.criminaldatabase.activities.PoliceUploadCriminalActivity.NO_DATA;

public class PoliceLoginActivity extends AppCompatActivity {
    private Button mlogin, mclear;
    private TextView mregistation;
    private EditText muser_name, mpassword;
    DatabaseHandler db;
    AlertDialog progressDialog;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_login);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if (prefs.getBoolean("loginflag", false)) {
            Intent i = new Intent(PoliceLoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }


        db = new DatabaseHandler(this);
        mlogin = (Button) findViewById(R.id.btn_login);
        mclear = (Button) findViewById(R.id.btn_clear);
        muser_name = (EditText) findViewById(R.id.edt_username);
        mpassword = (EditText) findViewById(R.id.edt_password);
        mregistation = (TextView) findViewById(R.id.txt_registration);
        checkLocation();

        mregistation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PoliceLoginActivity.this, PoliceRegistrationActivity.class);
                startActivity(i);
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (muser_name.getText().length() > 0 && mpassword.getText().length() > 0) {

                    new LoginApi(PoliceLoginActivity.this).execute("");

                } else {
                    Toast.makeText(PoliceLoginActivity.this, "Please Enter Username & Password ", Toast.LENGTH_SHORT).show();

                }

            }
        });
        mclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muser_name.setText("");
                mpassword.setText("");

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class LoginApi extends AsyncTask<String, Void, String> {
        Activity activity;

        public LoginApi(Activity activity) {
            this.activity = activity;
            progressDialog = new SpotsDialog(PoliceLoginActivity.this, R.style.Custom);


        }

        protected void onPreExecute() {
            Log.d("Service Order", "Updating the service order");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String returnValue = "";
            returnValue = checkLogin();

            return returnValue;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            if (result.length() > 0) {
                editor.putString("login", "" + muser_name.getText().toString());
                editor.putBoolean("loginflag", true);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result.toString());
                    editor.putInt("user_id", obj.getInt("id"));
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(PoliceLoginActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PoliceLoginActivity.this, MainActivity.class);
                startActivity(i);

            } else {
                Toast.makeText(PoliceLoginActivity.this, "Please Enter Username & Password ", Toast.LENGTH_SHORT).show();

            }


        }
    }

    public String checkLogin() {


        String imageUploadURL = "https://criminaldatabase.herokuapp.com/v1/sessions";

        Log.d("Service Order", "Uploading Image - Remote API " + imageUploadURL);

        int responseCode = NO_DATA;


        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(imageUploadURL);
            org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity();

            entity.addPart("email", new StringBody(muser_name.getText().toString(), "text/plain", Charset.forName("UTF-8")));
            entity.addPart("password", new StringBody(mpassword.getText().toString(), "text/plain", Charset.forName("UTF-8")));


            request.setEntity(entity);


            HttpResponse response = httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            String responseStr = EntityUtils.toString(response.getEntity());


            if (status == 201) {
                try {
                    return responseStr;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (status == 401) {
                Log.v("Unauthorized ", "" + responseStr);


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

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public void checkLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        } else {
            showGPSDisabledAlertToUser();
        }

    }

}
