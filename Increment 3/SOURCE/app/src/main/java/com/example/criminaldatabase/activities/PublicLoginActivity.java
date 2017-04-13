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
import android.widget.TextView;

import com.example.criminaldatabase.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import dmax.dialog.SpotsDialog;

import static com.example.criminaldatabase.activities.PoliceUploadCriminalActivity.NO_DATA;

public class PublicLoginActivity extends AppCompatActivity {

    private Button mlogin, mclear;
    private TextView mregistation;
    private EditText muser_name, mpassword;
    AlertDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_login);

        mlogin = (Button) findViewById(R.id.btn_login);
        mclear = (Button) findViewById(R.id.btn_clear);
        muser_name = (EditText) findViewById(R.id.edt_username);
        mpassword = (EditText) findViewById(R.id.edt_password);
        mregistation = (TextView) findViewById(R.id.txt_registration);


        mregistation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublicLoginActivity.this, PublicRegistrationActivity.class);
                startActivity(i);
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginApi(PublicLoginActivity.this).execute("");

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

    private class LoginApi extends AsyncTask<String, Void, String> {
        Activity activity;

        public LoginApi(Activity activity) {
            this.activity = activity;
            progressDialog = new SpotsDialog(PublicLoginActivity.this, R.style.Custom);


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
            Intent i = new Intent(PublicLoginActivity.this, PublicHomeActivity.class);
            startActivity(i);

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
}
