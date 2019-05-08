package com.example.loginandsplashscreen.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

public class ForgotPasswordActivity extends AppCompatActivity {


    AutoCompleteTextView text;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button send = findViewById(R.id.bu_forgotpassword_send);

        text = findViewById(R.id.tf_forgotpassword_id);
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

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar2);
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
}
