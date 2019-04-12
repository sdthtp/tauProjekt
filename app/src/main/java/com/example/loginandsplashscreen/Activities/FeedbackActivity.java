package com.example.loginandsplashscreen.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import android.app.Activity;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

public class FeedbackActivity extends AppCompatActivity {

    public static String token = "";
    private RatingBar myRatingBar;
    private EditText commentField;
    private ToggleButton toggleButton;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }


    public void showAlertDialog(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Feedback");
            alert.setMessage("Wollen Sie den Feedback senden?");

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(FeedbackActivity.this, "Feedback not sent", Toast.LENGTH_SHORT).show();
                }
            });

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    myRatingBar = (RatingBar) findViewById(R.id.rb_feedback_stars);
                    commentField = (EditText) findViewById(R.id.tf_feedback_text);

                    String star = String.valueOf(myRatingBar.getRating());
                    final String star2 = star.substring(0,1);
                    String text = commentField.getText().toString();
                    System.out.println(text);

                    NetworkHandling h = new NetworkHandling();

                    try {
                        String type;
                        ToggleButton b = findViewById(R.id.tb_feedback_type);
                        if (b.isChecked()) {
                            type = "shuttle";
                        } else {
                            type = "mensa";
                        }
                        String f = new NetworkHandling().execute("feedback",star,type,text,LoginActivity.token).get();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Toast.makeText(FeedbackActivity.this, "Feedback sent", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
    }

    public void onBackPressed(){
        Intent i = new Intent(FeedbackActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
