package com.example.loginandsplashscreen.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.Handlers.QRCodeHandler;
import com.example.loginandsplashscreen.R;

import org.w3c.dom.Text;

public class FeedbackActivity extends AppCompatActivity {

    public static String token = "";
    private RatingBar myRatingBar;
    private EditText commentField;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);



        /*Button feedbackButton = (Button) findViewById(R.id.buttonFeedback2);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });*/
    }

    /*private void sendFeedback() {


    }*/

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

                    myRatingBar = (RatingBar) findViewById(R.id.ratingBar2);
                    commentField = (EditText) findViewById(R.id.editText2);
                    //toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

                    //boolean checked = ((ToggleButton)v).isChecked();

                    String star = String.valueOf(myRatingBar.getRating());;
                    star = star.substring(0,1);
                    String text = commentField.getText().toString();
                    NetworkHandling h = new NetworkHandling();
                    try {
                        //h.execute("feedback",star,text,token).get();

                        /*if(checked){
                            String f = new NetworkHandling().execute("feedback",star,"mensa",text,LoginActivity.token).get();
                            System.out.println(f);
                        }
                        else{
                            String f = new NetworkHandling().execute("feedback",star,"shuttle",text,LoginActivity.token).get();
                            System.out.println(f);
                        }*/
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
