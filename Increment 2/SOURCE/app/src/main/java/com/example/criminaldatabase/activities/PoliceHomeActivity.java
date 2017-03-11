package com.example.criminaldatabase.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.criminaldatabase.R;

public class PoliceHomeActivity extends AppCompatActivity {

    private Button mupload_criminal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_home);

        mupload_criminal=(Button)findViewById(R.id.btn_upload_criminal);

        mupload_criminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PoliceHomeActivity.this,PoliceUploadCriminalActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
