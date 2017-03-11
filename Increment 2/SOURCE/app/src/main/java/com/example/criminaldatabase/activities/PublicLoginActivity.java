package com.example.criminaldatabase.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.criminaldatabase.R;

public class PublicLoginActivity  extends AppCompatActivity{

    private Button mlogin,mclear;
    private TextView mregistation;
    private EditText muser_name,mpassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_login);

        mlogin=(Button)findViewById(R.id.btn_login);
        mclear=(Button)findViewById(R.id.btn_clear);
        muser_name=(EditText)findViewById(R.id.edt_username);
        mpassword=(EditText)findViewById(R.id.edt_password);
        mregistation=(TextView)findViewById(R.id.txt_registration);


        mregistation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PublicLoginActivity.this,PublicRegistrationActivity.class);
                startActivity(i);
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PublicLoginActivity.this,PublicHomeActivity.class);
                startActivity(i);
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
}
