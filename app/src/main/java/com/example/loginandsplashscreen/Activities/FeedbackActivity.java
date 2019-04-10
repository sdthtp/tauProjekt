package com.example.loginandsplashscreen.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.Handlers.QRCodeHandler;
import com.example.loginandsplashscreen.R;

import org.w3c.dom.Text;

public class FeedbackActivity extends AppCompatActivity {

    public static String token = "";
    private RatingBar myRatingBar;
    private EditText commentField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        myRatingBar = (RatingBar) findViewById(R.id.ratingBar2);
        commentField = (EditText) findViewById(R.id.editText2);

        Button feedbackButton = (Button) findViewById(R.id.buttonFeedback2);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });
    }

    private void sendFeedback() {


        String star = String.valueOf(myRatingBar.getRating());;
        star = star.substring(0,1);
        String text = commentField.getText().toString();

        NetworkHandling h = new NetworkHandling();
        try {
            //h.execute("feedback",star,text,token).get();
            String f = new NetworkHandling().execute("feedback",star,text,LoginActivity.token).get();

            System.out.println(f);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
