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

public class MainActivity extends AppCompatActivity {

    private Button mpublic_btn;
    private Button mpolice_btn;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        mpublic_btn=(Button)findViewById(R.id.btn_public_module);
        mpolice_btn=(Button)findViewById(R.id.btn_police_module);

        mpublic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,PublicHomeActivity.class);
                startActivity(i);

            }
        });
        mpolice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,PoliceHomeActivity.class);
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
                Intent i = new Intent(MainActivity.this, PoliceLoginActivity.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
