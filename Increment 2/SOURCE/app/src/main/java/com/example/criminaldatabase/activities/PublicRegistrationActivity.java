package com.example.criminaldatabase.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.criminaldatabase.R;

public class PublicRegistrationActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_registration);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
