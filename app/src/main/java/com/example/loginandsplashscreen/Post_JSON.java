package com.example.loginandsplashscreen;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.loginandsplashscreen.JsonedClasses.Amount;
import com.example.loginandsplashscreen.JsonedClasses.PriceId;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;

public class Post_JSON {

    //used to get authentication token


    public static Object getToken(String json) {
        try {
            System.out.println(json);
            //URL url = new URL("HTTP",query_url, Constants.PORT,"/login");
            URL url = new URL("http://" + Constants.QUERY + ":"+ Constants.PORT + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            String token = conn.getHeaderField("Authorization");
            conn.disconnect();
            return token;
        } catch (Exception e) {
            return e.toString();
        }
    }

    //JSON is sent authorized + response is read (body)
    public static Object Post_JSON(String json, String file, String key, String path){
        try {
            //System.out.println(json);
            String query_url = Constants.QUERY;
            //URL url = new URL("HTTP",query_url, Constants.PORT,file);
            URL url = new URL("http://" + query_url + ":"+ Constants.PORT + file);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty(key,path);
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            in.close();
            conn.disconnect();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    //JSON is sent unauthorized + response is read
    public static Object Post_JSON(String json, String file){
        try {
            System.out.println(json);
            String query_url = Constants.QUERY;
            //URL url = new URL("HTTP",query_url, Constants.PORT,file);
            URL url = new URL("http://" + query_url + ":"+ Constants.PORT + file);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            in.close();
            conn.disconnect();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    //no json is sent, only headerfield file is sent ("Authorization") along with the key (token), to then get a response in the body
    public static Object readResponse(String file, String key, String path) {
        try {
            String query_url = Constants.QUERY;
            //URL url = new URL("HTTP",query_url, Constants.PORT,file);
            URL url = new URL("http://" + query_url + ":"+ Constants.PORT + file);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty(key, path);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            in.close();
            conn.disconnect();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    //return account information
    public static String getInfo(String token) {
        try {
            return (String)readResponse("/customers/get-info","Authorization",token);
        } catch (Exception e) {
            System.out.println(e);
            return e.toString();
        }
    }

    //logs the user in and return the token;
    public static String login(String id, String password) {
        String login = "{ \"id\": \"" + id + "\", \"password\": \"" + password + "\" }";
        System.out.println(login);
        return (String)getToken(login);
    }

    public static String forgotPassword(String id) {
        String jsoned = "{\"id\": \"" + id + "\"}";
        return (String)Post_JSON(jsoned,"/customers/forgot-password");
    }

    public static String changepassword(String oldPass, String newPass, String token) {
        String jsoned = "{\"oldPass\":\""+ oldPass + "\", \"newPass\": \"" + newPass + "\" }";
        return (String)Post_JSON(jsoned,"/customers/change-password","Authorization",token);
    }

    public static String transfer(String receiverId, String balanceId, int amount, String token) {
        String jsoned = "{\"receiverId\": \"" + receiverId + "\",\"balanceId\": \"" + balanceId +"\",\"amount\": " + amount + " }";
        return (String)Post_JSON(jsoned,"/customers/transfer","Authorization",token);
    }

    //TODO: Backend must implement this
    public static String freeItem(String priceid, int amount, String token) {
        String jsoned = "{\"priceId\": \"" + priceid + "\",\"amount\": " + amount + " }";
        return (String)Post_JSON(jsoned,"/customers/donate-item","Authorization",token);
    }

    public static String empfehlen(String id){
        String jsoned = "{\"id\": \"" + id + "\"}";
        // TODO: Edit in backend
        return (String)Post_JSON(jsoned,"/customers/forgot-password");
    }

    public static String isPaid(String qrcode, String token) {
        String jsoned = "{\"qrCode\": \"" + qrcode + "\" }";
        return (String)Post_JSON.Post_JSON(jsoned,"/customers/is-paid","Authorization",token);
    }




    //request QR-Code String from Backend and generate the image
    public static String requestQRCode(String token) {
        String text = (String)readResponse("/customers/request-qr-code","Authorization",token);
        return text;
    }

    public static String feedback(int star, String type, String feedbackText, String token){
        feedbackText = feedbackText.replaceAll("\n"," ");
        String jsoned = "{\"star\": " + star + ",\"type\": \"" + type + "\",\"text\": \"" + feedbackText + "\"}";
        System.out.println(jsoned);
        return (String)Post_JSON(jsoned,"/customers/feedback","Authorization",token);
    }
}
