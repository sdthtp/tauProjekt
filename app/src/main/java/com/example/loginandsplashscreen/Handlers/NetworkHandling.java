package com.example.loginandsplashscreen.Handlers;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.loginandsplashscreen.Handlers.InstructionHandler;
import com.example.loginandsplashscreen.Post_JSON;


public class NetworkHandling extends AsyncTask<String,Void,String> {

    protected String doInBackground(String... param) {
        int operation = InstructionHandler.getInstruction(param[0]);
        if (operation == 1) {
            return Post_JSON.login(param[1],param[2]);
        } else if (operation == 2) {
            return Post_JSON.getInfo(param[1]);
        } else if (operation == 3) {
            return Post_JSON.changepassword(param[1],param[2],param[3]);
        } else if (operation == 4) {
            return Post_JSON.transfer(param[1],param[2],Integer.parseInt(param[3]),param[4]);
        } else if (operation == 5) {
            return Post_JSON.forgotPassword(param[1]);
        } else if (operation == 6) {
            return Post_JSON.requestQRCode(param[1]);
        } else if (operation == 7) {
            return Post_JSON.freeItem(param[1], Integer.parseInt(param[2]), param[3]);
        } else if (operation == 8) {
            return Post_JSON.empfehlen(param[1]);
        } else if (operation == 9) {
            return Post_JSON.feedback(Integer.parseInt(param[1]), param[2], param[3], param[4]);
        } /*else if (operation == 10) {
            try {
                Document doc = Jsoup.connect("http://www.tau.edu.tr/saglikkulturspor/yemek_menusu").get();
                return doc.body().getElementsByTag("table").get(0).text();
            } catch (Exception e) {
                return "Error while trying to resolve tau.edu.tr";
            }*/

        //}
        else {
            return "Error while trying to do the operation at NetworkHandling!";
        }
    }

    protected void onPreExecute(Void test) {
    }

    protected String onPostExecute(String... result) {
        return result[0];
    }

}
