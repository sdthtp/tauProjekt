package com.example.loginandsplashscreen.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

public class ForgotPasswordActivity extends AppCompatActivity {


    AutoCompleteTextView text = findViewById(R.id.tf_forgotpassword_id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button send = findViewById(R.id.bu_forgotpassword_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new NetworkHandling().execute("forgotPassword",text.getText().toString()).get();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });


    }
}
