package com.example.criminaldatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.criminaldatabase.R;

public class MainActivity extends AppCompatActivity {

    private Button mpublic_btn;
    private Button mpolice_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mpublic_btn=(Button)findViewById(R.id.btn_public_module);
        mpolice_btn=(Button)findViewById(R.id.btn_police_module);

        mpublic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,PublicLoginActivity.class);
                startActivity(i);

            }
        });
        mpolice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,PoliceLoginActivity.class);
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
