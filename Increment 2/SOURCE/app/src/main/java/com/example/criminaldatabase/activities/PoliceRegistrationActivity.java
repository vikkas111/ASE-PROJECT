package com.example.criminaldatabase.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.criminaldatabase.R;
import com.example.criminaldatabase.database.DatabaseHandler;
import com.example.criminaldatabase.model.Police;

import java.util.List;

public class PoliceRegistrationActivity extends AppCompatActivity {
    private EditText mFull_name, mEmail_id, mMobile_no, mAddress, mUsername, mPassword;
    private Button msubmit, mclear;
    DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_registration);

        db = new DatabaseHandler(this);
        mFull_name = (EditText) findViewById(R.id.edt_fullname);
        mEmail_id = (EditText) findViewById(R.id.edt_emailId);
        mMobile_no = (EditText) findViewById(R.id.edt_mobile);
        mAddress = (EditText) findViewById(R.id.edt_address);
        mUsername = (EditText) findViewById(R.id.edt_username);
        mPassword = (EditText) findViewById(R.id.edt_password);

        msubmit = (Button) findViewById(R.id.btn_submit);
        mclear = (Button) findViewById(R.id.btn_clear);
        mclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFull_name.setText("");
                mEmail_id.setText("");
                mMobile_no.setText("");
                mAddress.setText("");
                mUsername.setText("");
                mPassword.setText("");
            }
        });
        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsername.getText().length() > 0 && mPassword.getText().length() > 0) {
                    Police p = new Police();
                    p.set_fullname(mFull_name.getText().toString());
                    p.set_email_id(mEmail_id.getText().toString());
                    p.set_address(mAddress.getText().toString());
                    p.set_mobile(mMobile_no.getText().toString());
                    p.set_username(mUsername.getText().toString());
                    p.set_password(mPassword.getText().toString());
                    db.addPolice(p);

                    List<Police> police = db.getAllPolice();
                    Toast.makeText(PoliceRegistrationActivity.this, "Successfully Registred ", Toast.LENGTH_SHORT).show();

                    for (Police cn : police) {
                        String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_username() + " ,Phone: " + cn.get_password();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                    }
                    finish();
                } else {
                    Toast.makeText(PoliceRegistrationActivity.this, "Please Enter Username & Password ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
