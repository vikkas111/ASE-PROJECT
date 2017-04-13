package com.example.criminaldatabase.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.criminaldatabase.R;

public class PoliceHomeActivity extends AppCompatActivity {

    private Button mupload_criminal,btn_view_reported_crime,btn_search_criminals;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_home);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();


        mupload_criminal=(Button)findViewById(R.id.btn_upload_criminal);
        btn_view_reported_crime=(Button)findViewById(R.id.btn_view_reported_crime);
        btn_search_criminals=(Button)findViewById(R.id.btn_search_criminals);

        mupload_criminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PoliceHomeActivity.this,PoliceUploadCriminalActivity.class);
                startActivity(i);
            }
        });


        btn_view_reported_crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PoliceHomeActivity.this,ViewReportsActivity.class);
                startActivity(i);
            }
        });
        btn_search_criminals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PoliceHomeActivity.this,PoliceViewCriminalsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                editor.clear();
                editor.commit();
                Intent i = new Intent(PoliceHomeActivity.this, PoliceLoginActivity.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
