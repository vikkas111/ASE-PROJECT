package com.example.criminaldatabase.activities;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import java.util.List;

public class PoliceUploadCriminalActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private EditText mEdit_crimelocation, medt_criminalname, medt_emailId,
            medt_mobile, medt_address, medt_crimerecord_no, medt_crimedescription;
    private Button mbuttonLoadImage, mbtn_upload, mbtn_clear;
    private ImageView imageView;
    private String imagepath="";
    DatabaseHandler db;

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
                if (medt_criminalname.getText().length() > 0 && mEdit_crimelocation.getText().length() > 0
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
                    db.addUpload(p);

                    List<UploadCriminal> uploadcriminal = db.getAllUPLOADED_CRIMINALS();
                    Toast.makeText(PoliceUploadCriminalActivity.this, "Successfully Record Uploaded ", Toast.LENGTH_SHORT).show();

                    for (UploadCriminal cn : uploadcriminal) {
                        String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_criminalname()+ " ,path: " + cn.get_image()+" Loc: "+cn.get_crime_location();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                    }
                    finish();


                }else{
                    Toast.makeText(PoliceUploadCriminalActivity.this,"Enter the fields",Toast.LENGTH_SHORT).show();
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

            imagepath=picturePath;
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setVisibility(View.VISIBLE);

        }

    }
}
