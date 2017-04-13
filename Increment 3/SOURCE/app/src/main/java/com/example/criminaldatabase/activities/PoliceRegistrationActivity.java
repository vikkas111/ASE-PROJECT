package com.example.criminaldatabase.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import dmax.dialog.SpotsDialog;

import static com.example.criminaldatabase.activities.PoliceUploadCriminalActivity.NO_DATA;

public class PoliceRegistrationActivity extends AppCompatActivity {
    private EditText mReTypePassword, mEmail_id, mMobile_no, mAddress, mName, mPassword;
    private Button msubmit, mclear;
    DatabaseHandler db;
    AlertDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_registration);

        db = new DatabaseHandler(this);
        mReTypePassword = (EditText) findViewById(R.id.edt_retypepassword);
        mEmail_id = (EditText) findViewById(R.id.edt_emailId);
        mMobile_no = (EditText) findViewById(R.id.edt_mobile);
        mAddress = (EditText) findViewById(R.id.edt_address);
        mName = (EditText) findViewById(R.id.edt_name);
        mPassword = (EditText) findViewById(R.id.edt_password);

        msubmit = (Button) findViewById(R.id.btn_submit);
        mclear = (Button) findViewById(R.id.btn_clear);
        mclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReTypePassword.setText("");
                mEmail_id.setText("");
                mMobile_no.setText("");
                mAddress.setText("");
                mName.setText("");
                mPassword.setText("");
            }
        });
        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mName.getText().length() > 0 && mPassword.getText().length() > 0) {

                    if (mReTypePassword.getText().length() > 0) {
                        if (mReTypePassword.getText().toString().equals(mPassword.getText().toString())) {
                            new RegisterApi(PoliceRegistrationActivity.this).execute("");
                        } else {
                            Toast.makeText(PoliceRegistrationActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PoliceRegistrationActivity.this, "Please Enter Retype Password Field", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(PoliceRegistrationActivity.this, "Please Enter Username & Password ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class RegisterApi extends AsyncTask<String, Void, String> {
        Activity activity;

        public RegisterApi(Activity activity) {
            this.activity = activity;
            progressDialog = new SpotsDialog(PoliceRegistrationActivity.this, R.style.Custom);


        }

        protected void onPreExecute() {
            Log.d("Service Order", "Updating the service order");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String returnValue = "";
            returnValue = registration();

            return returnValue;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            if (result.length() > 0) {

                try {
                    if (isJSONValid(result)) {
                        JSONObject obj = new JSONObject(result);
                        if (obj.has("email")) {
                            Toast.makeText(PoliceRegistrationActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PoliceRegistrationActivity.this, PoliceLoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(PoliceRegistrationActivity.this, "" + result, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(PoliceRegistrationActivity.this, "Registration was Unsuccessfull", Toast.LENGTH_SHORT).show();

            }


        }
    }

    public String registration() {


        String imageUploadURL = "https://criminaldatabase.herokuapp.com/v1/user";

        Log.d("Service Order", "Uploading Image - Remote API " + imageUploadURL);

        int responseCode = NO_DATA;


        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(imageUploadURL);
            org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity();

            entity.addPart("email", new StringBody(mEmail_id.getText().toString(), "text/plain", Charset.forName("UTF-8")));
            entity.addPart("password", new StringBody(mPassword.getText().toString(), "text/plain", Charset.forName("UTF-8")));
            entity.addPart("name", new StringBody(mName.getText().toString(), "text/plain", Charset.forName("UTF-8")));
            entity.addPart("mobile", new StringBody(mMobile_no.getText().toString(), "text/plain", Charset.forName("UTF-8")));
            entity.addPart("address", new StringBody(mAddress.getText().toString(), "text/plain", Charset.forName("UTF-8")));


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

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
