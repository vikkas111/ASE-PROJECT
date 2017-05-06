package com.example.criminaldatabase.activities;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.criminaldatabase.R;

public class PublicHomeActivity extends AppCompatActivity {

    private Button mbtn_view_criminals, emergency;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_home);
        MainActivity.policehome = false;
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        mbtn_view_criminals = (Button) findViewById(R.id.btn_view_criminals);
        emergency = (Button) findViewById(R.id.btn_emergency);
        mbtn_view_criminals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublicHomeActivity.this, ViewCriminalsActivity.class);
                startActivity(i);
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.policehome = false;
                Intent intent = new Intent();
                intent.setAction("com.police.home");
                intent.putExtra("url", "sudarshan");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(PublicHomeActivity.this, 1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pendingIntent);
                Toast.makeText(PublicHomeActivity.this, "Emergency call has triggered to Police ", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(PublicHomeActivity.this, PoliceLoginActivity.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
