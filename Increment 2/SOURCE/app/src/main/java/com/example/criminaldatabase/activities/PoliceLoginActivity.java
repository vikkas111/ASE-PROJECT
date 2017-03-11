package com.example.criminaldatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.criminaldatabase.R;
import com.example.criminaldatabase.database.DatabaseHandler;
import com.example.criminaldatabase.model.Police;

import java.util.List;

public class PoliceLoginActivity extends AppCompatActivity {
    private Button mlogin, mclear;
    private TextView mregistation;
    private EditText muser_name, mpassword;
    DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_login);
        db = new DatabaseHandler(this);
        mlogin = (Button) findViewById(R.id.btn_login);
        mclear = (Button) findViewById(R.id.btn_clear);
        muser_name = (EditText) findViewById(R.id.edt_username);
        mpassword = (EditText) findViewById(R.id.edt_password);
        mregistation = (TextView) findViewById(R.id.txt_registration);


        mregistation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PoliceLoginActivity.this,PoliceRegistrationActivity.class);
                startActivity(i);
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (muser_name.getText().length() > 0 && mpassword.getText().length() > 0) {
                    List<Police> police = db.getAllPolice();
                    boolean flag=false;

                    for (Police cn : police) {
                        if(cn.get_username().equals(muser_name.getText().toString()) &&  cn.get_password().equals(mpassword.getText().toString())){
                            flag=true;
                            Intent i = new Intent(PoliceLoginActivity.this, PoliceHomeActivity.class);
                            startActivity(i);
                            break;
                        }
                    }
                    if(!flag){
                        Toast.makeText(PoliceLoginActivity.this, "Invalid Username & Password ", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(PoliceLoginActivity.this, "Please Enter Username & Password ", Toast.LENGTH_SHORT).show();

                }

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
