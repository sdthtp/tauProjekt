package com.example.loginandsplashscreen.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

public class FeedbackActivity extends AppCompatActivity {

    public static String token = "";
    private RatingBar myRatingBar;
    private EditText commentField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        myRatingBar = (RatingBar) findViewById(R.id.rb_feedback_stars);
        commentField = (EditText) findViewById(R.id.tf_feedback_text);

        Button feedbackButton = (Button) findViewById(R.id.bu_feedback_send);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });
    }

    private void sendFeedback() {


        String star = String.valueOf(myRatingBar.getRating());
        star = star.substring(0,1);
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

            System.out.println(f);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
