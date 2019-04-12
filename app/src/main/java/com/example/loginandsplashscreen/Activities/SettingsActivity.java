package com.example.loginandsplashscreen.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.Dialog;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.settingsToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Button logout = (Button) findViewById(R.id.logoutBtn);
        Button changePassword = (Button) findViewById(R.id.passwordChangeBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

                LayoutInflater inflater = SettingsActivity.this.getLayoutInflater();

                final EditText altespasswort = (EditText) findViewById(R.id.editText2);
                final EditText neuespasswort = (EditText) findViewById(R.id.editText3);


                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.dialog_changepassword, null));

                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingsActivity.this, "Abgebrochen", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("Ändern", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String alt = altespasswort.getText().toString();
                        String neu = neuespasswort.getText().toString();

                        NetworkHandling h = new NetworkHandling();

                        try {
                            String f = new NetworkHandling().execute("changepassword",alt,neu,LoginActivity.token).get();
                            System.out.println(f);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        Toast.makeText(SettingsActivity.this, "Feedback sent", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
            }
        });
    }



   /* public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_changepassword, null))
                // Add action buttons
                .setPositiveButton("Ändern", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(SettingsActivity.this, "Feedback not sent", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(SettingsActivity.this, "Password not changed", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }*/

    public void onBackPressed(){
        Intent i = new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
