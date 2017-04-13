package com.example.criminaldatabase.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.criminaldatabase.R;
import com.example.criminaldatabase.database.DatabaseHandler;
import com.example.criminaldatabase.maps.MapsActivity;
import com.example.criminaldatabase.model.UploadCriminal;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import dmax.dialog.SpotsDialog;

public class PoliceUploadCriminalActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private EditText mEdit_crimelocation, medt_criminalname, medt_emailId,
            medt_mobile, medt_address, medt_crimerecord_no, medt_crimedescription;
    private Button mbuttonLoadImage, mbtn_upload, mbtn_clear;
    private ImageView imageView;
    private String imagepath = "";
    DatabaseHandler db;

    public static int NO_DATA = 301;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_uploadcriminal);
        db = new DatabaseHandler(this);
        mEdit_crimelocation = (EditText) findViewById(R.id.edt_crimelocation);
        medt_criminalname = (EditText) findViewById(R.id.edt_criminalname);
        medt_emailId = (EditText) findViewById(R.id.edt_emailId);
        medt_mobile = (EditText) findViewById(R.id.edt_mobile);
        medt_address = (EditText) findViewById(R.id.edt_address);
        medt_crimerecord_no = (EditText) findViewById(R.id.edt_crimerecord_no);
        medt_crimedescription = (EditText) findViewById(R.id.edt_crimedescription);
        medt_criminalname = (EditText) findViewById(R.id.edt_criminalname);

        mbtn_upload = (Button) findViewById(R.id.btn_upload);

        mbtn_clear = (Button) findViewById(R.id.btn_clear);

        imageView = (ImageView) findViewById(R.id.imgView);

        mbtn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdit_crimelocation.setText("");
                medt_criminalname.setText("");
                medt_emailId.setText("");
                medt_mobile.setText("");
                medt_address.setText("");
                medt_crimerecord_no.setText("");
                medt_crimedescription.setText("");
                medt_criminalname.setText("");
                imageView.setVisibility(View.GONE);
            }
        });

        mbtn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medt_criminalname.getText().length() > 0
                        && medt_crimerecord_no.getText().length() > 0) {
                    UploadCriminal p = new UploadCriminal();
                    p.set_criminalname(medt_criminalname.getText().toString());
                    p.set_email_id(medt_emailId.getText().toString());
                    p.set_address(medt_address.getText().toString());
                    p.set_mobile(medt_mobile.getText().toString());
                    p.set_criminal_rec_no(medt_crimerecord_no.getText().toString());
                    p.set_crime_description(medt_crimedescription.getText().toString());
                    p.set_image(imagepath);
                    p.set_crime_location(mEdit_crimelocation.getText().toString());
                //    db.addUpload(p);
                    new CallApi(PoliceUploadCriminalActivity.this).execute("");




                } else {
                    Toast.makeText(PoliceUploadCriminalActivity.this, "Enter the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mEdit_crimelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PoliceUploadCriminalActivity.this, MapsActivity.class);
                startActivityForResult(i, 2);

            }
        });

        mbuttonLoadImage = (Button) findViewById(R.id.btn_Uploadphoto);
        mbuttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2 && data != null) {
            String message = data.getStringExtra("MESSAGE");
            mEdit_crimelocation.setText(message);
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imagepath = picturePath;
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setVisibility(View.VISIBLE);


        }

    }

    private class CallApi extends AsyncTask<String, Void, Integer> {
        Activity activity;
        AlertDialog progressDialog;
        public CallApi(Activity activity) {

            this.activity = activity;
            progressDialog = new SpotsDialog(PoliceUploadCriminalActivity.this, R.style.Custom);
        }

        protected void onPreExecute() {
            Log.d("Service Order", "Updating the service order");
            progressDialog.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
            int returnValue = 0;
            uploadPictureImage();

            return returnValue;
        }

        protected void onPostExecute(Integer result) {
            progressDialog.dismiss();
            finish();
        }
    }

    public int uploadPictureImage() {


        String imageUploadURL = "https://criminaldatabase.herokuapp.com/" + "criminals";

        Log.d("Service Order", "Uploading Image - Remote API " + imageUploadURL);

        int responseCode = NO_DATA;


        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(imageUploadURL);
            request.setHeader("Content-type", "application/json");
            org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity();

            JSONObject obj = new JSONObject();
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("criminalname", medt_criminalname.getText().toString());
                jsonObj.put("criminalname", medt_criminalname.getText().toString());
                jsonObj.put("email_id", medt_emailId.getText().toString());
                jsonObj.put("mobile", medt_mobile.getText().toString());
                jsonObj.put("address", medt_address.getText().toString());
                jsonObj.put("criminal_rec_no", medt_crimerecord_no.getText().toString());
                jsonObj.put("description", medt_crimedescription.getText().toString());
                jsonObj.put("crime_location", mEdit_crimelocation.getText().toString());

                JSONObject imgjsonObj = new JSONObject();

                imgjsonObj.put("file", getFileToByte(imagepath));
                File image = new File(imagepath);

                imgjsonObj.put("original_filename", image.getName());
                imgjsonObj.put("filename", image.getName());
                jsonObj.accumulate("image", imgjsonObj);

                obj.accumulate("criminal", jsonObj);

            } catch (Exception e) {

            }
            StringEntity se = new StringEntity(obj.toString());
            //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(se);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            String responseStr = EntityUtils.toString(response.getEntity());
            try {
                JSONObject objasf = new JSONObject(responseStr);


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
        return responseCode;

    }

    public static String getFileToByte(String path) {
        Bitmap bm = null;
        ByteArrayOutputStream baos = null;
        byte[] b = null;
        String encodeString = null;
        try {
            bm = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            b = baos.toByteArray();
            encodeString = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }
}
