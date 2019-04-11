package com.example.loginandsplashscreen.Handlers;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.loginandsplashscreen.Handlers.InstructionHandler;
import com.example.loginandsplashscreen.Post_JSON;

public class NetworkHandling extends AsyncTask<String,Void,String> {

    protected String doInBackground(String... json) {
        int operation = InstructionHandler.getInstruction(json[0]);
        if (operation == 1) {
            return Post_JSON.login(json[1],json[2]);
        } else if (operation == 2) {
            return Post_JSON.getInfo(json[1]);
        } else if (operation == 3) {
            return Post_JSON.changepassword(json[1],json[2]);
        } else if (operation == 4) {
            return Post_JSON.transfer(json[1],json[2],Integer.parseInt(json[3]),json[4]);
        } else if (operation == 5) {
            return Post_JSON.forgotPassword(json[1]);
        } else if (operation == 6) {
            return Post_JSON.getQRCode(json[1]);
        } else if (operation == 7) {
            return Post_JSON.freeItem(json[1], Integer.parseInt(json[2]), json[3]);
        } else if (operation == 8) {
            return Post_JSON.empfehlen(json[1]);
        } else if (operation == 9) {
            return Post_JSON.feedback(Integer.parseInt(json[1]), json[2], json[3], json[4]);
        } else {
            return "Error while trying to do the operation at NetworkHandling!";
        }
    }

    protected void onPreExecute(Void test) {
    }

    protected String onPostExecute(String... result) {
        return result[0];
    }

}
