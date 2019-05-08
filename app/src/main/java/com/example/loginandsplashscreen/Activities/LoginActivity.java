package com.example.loginandsplashscreen.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;


public class LoginActivity extends AppCompatActivity {
    public static String token = "";
    private AutoCompleteTextView mIDView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        token = null;
        SharedPreferences preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("var1", null);
        editor.commit();
        mIDView = findViewById(R.id.tf_login_id);

        mPasswordView = findViewById(R.id.tf_login_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });



        Button mEmailSignInButton = (Button) findViewById(R.id.bu_login_login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        Button mPwvergessen = findViewById(R.id.bu_login_forgotpassword);
        mPwvergessen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }


    private void attemptLogin() {
        mIDView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String id = mIDView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(id)) {
            mIDView.setError(getString(R.string.ogrenci_numaranizi_girin));
            focusView = mIDView;
            cancel = true;
        }
        NetworkHandling h = new NetworkHandling();
        try {
            //with .execute(firstparameter is instruction)
            token = h.execute("login",id,password).get();
            SharedPreferences preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("var1", token);
            editor.commit();

        } catch (Exception e) {
            System.out.println(e);
        }


        System.out.println(token);
        // Check for a valid id address.
        if (isInfoValid(token)) {
            mIDView.setError(getString(R.string.ogrenci_numarasi_veya_sifre_yanlis));
            focusView = mIDView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            String k="";
            NetworkHandling h1 = new NetworkHandling();
            try {
                k = h1.execute("getInfo",token).get();
            } catch (Exception e) {
                System.out.println(e);
            }
            NetworkHandling h2 = new NetworkHandling();
            try {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            } catch (Exception e) {
                System.out.println("java exception" + e);
            }

        }
    }

    private boolean isInfoValid(String token) {
        try {
            return token==null || !token.substring(0,6).equals("Bearer");
        } catch (Exception e) {
            System.out.println("Exception in isInfoValid: " + e);
            return true;
        }
    }
}

