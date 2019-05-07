package com.example.loginandsplashscreen.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import java.util.Locale;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_settings);

        Button changeLang = (Button) findViewById(R.id.changeLanguage);

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });


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


        //TODO: implement onClickListener for language togglebutton and implement its function

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


                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                builder.setNegativeButton("İptal Et", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setPositiveButton("Değiştir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText altespasswort = ((AlertDialog) dialog).findViewById(R.id.tf_changepassword_old);
                        EditText neuespasswort = ((AlertDialog) dialog).findViewById(R.id.tf_changepassword_new);
                        EditText neuespasswortconfirm = ((AlertDialog) dialog).findViewById(R.id.tf_changepassword_new_confirm);
                        String alt = altespasswort.getText().toString();
                        String neu = neuespasswort.getText().toString();
                        String neuconfirm = neuespasswortconfirm.getText().toString();

                        NetworkHandling h = new NetworkHandling();

                        try {
                            if(!neu.equals(neuconfirm)){
                                Toast.makeText(SettingsActivity.this, "Şifre uyuşmuyor", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String f = new NetworkHandling().execute("changePassword",alt,neu,LoginActivity.token).get();
                                System.out.println(f);
                                Toast.makeText(SettingsActivity.this, "Şifre değiştirildi", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                });
                builder.setView(R.layout.dialog_changepassword); AlertDialog l = builder.create();
                l.show();


            }
        });
    }

    private void showChangeLanguageDialog(){
        final String[] listItems = {"Türkce", "Almanca"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listItems,-1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    setLocale("tr");
                    recreate();
                }
                else if(i == 1){
                    setLocale("de");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);

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
