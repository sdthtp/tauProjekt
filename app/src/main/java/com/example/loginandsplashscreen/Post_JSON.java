package com.example.loginandsplashscreen;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.loginandsplashscreen.JsonedClasses.Amount;
import com.example.loginandsplashscreen.JsonedClasses.PriceId;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
public class Post_JSON {

    //used to get authentication token


    public static Object getToken(String json) {
        try {
            System.out.println(json);
            String query_url = Constants.QUERY;
            URL url = new URL("HTTP",query_url, Constants.PORT,"/login");
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
            URL url = new URL("HTTP",query_url, Constants.PORT,file);
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
            URL url = new URL("HTTP",query_url, Constants.PORT,file);
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
            URL url = new URL("HTTP",query_url, Constants.PORT,file);
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

    //TODO: not used anymore
    /*public static String register(String id, String name, String mail, String password) {
        Gson gson = new Gson();
        Customer student = new Customer(id, name, mail, password);
        String studentinformation = gson.toJson(student);
        System.out.println(studentinformation);
        return (String)Post_JSON(studentinformation,"/customers/sign-up","Content-Type","application/json; charset=UTF-8");
    }*/

    //logs the user in and return the token;
    public static String login(String id, String password) {
        String login = "{ \"id\": \"" + id + "\", \"password\": \"" + password + "\" }";
        System.out.println(login);
        return (String)getToken(login);
    }

    //deposits the amount given in the parameters TODO: not used anymore
    public static String deposit(int amount, String token) {
        Amount a = new Amount(amount);
        return (String)Post_JSON(new Gson().toJson(a),"/customers/deposit","Authorization",token);
    }

    //TODO: deprecated, not used anymore
    public static String payOld(String priceId, String token) {
        //String type = "{ \"priceId\":" + priceId + "}";
        PriceId priceid1 = new PriceId(priceId);
        return (String)Post_JSON(new Gson().toJson(priceid1),"/customers/pay","Authorization",token);
    }

    public static String forgotPassword(String id) {
        String jsoned = "{\"id\": \"" + id + "\"}";
        return (String)Post_JSON(jsoned,"/customers/forgot-password");
    }

    public static String changepassword(String newPass, String token) {
        String jsoned = "{\"newPass\": \"" + newPass + "\" }";
        return (String)Post_JSON(jsoned,"/customers/change-password","Authorization",token);
    }

    public static String transfer(String receiverId, String balanceId, int amount, String token) {
        String jsoned = "{\"receiverId\": \"" + receiverId + "\",\"balanceId\": \"" + balanceId +"\",\"amount\": " + amount + " }";
        return (String)Post_JSON(jsoned,"/customers/transfer","Authorization",token);
    }

    //TODO:
    public static String freeItem(int amount,String priceid,String token) {
        String jsoned = "{\"priceid\": \"" + priceid + "\",\"amount\": " + amount + " }";
        return (String)Post_JSON(jsoned,"/customers/donate-item","Authorization",token);
    }

    public static String empfehlen(String id){
        String jsoned = "{\"id\": \"" + id + "\"}";
        // TODO: Edit in backend
        return (String)Post_JSON(jsoned,"/customers/forgot-password");
    }

    //TODO: not used anymore
    /*public static String pay (String token) throws IOException, InterruptedException{
        String qrcode = QRCodeHandler.decodeQRCode(Post_JSON.getQRCode(token));
        String jsoned = "{\"qrCode\": \"" + qrcode + "\" }";
        while (true) {
            String k = (String)Post_JSON.Post_JSON(jsoned,"/customers/is-paid","Authorization",token);
            System.out.println("Waiting for Payment to process!");
            if (k.equals("true")) {
                return "Payment complete! Respect+!";
            } else {
                Thread.sleep(1000);
            }
        }
    }*/

    //request QR-Code String from Backend and generate the image
    public static String getQRCode(String token) {
        String text = (String)readResponse("/customers/request-qr-code","Authorization",token);
        /*Bitmap m = null;
        try {
            m = QRCodeHandler.generateQRCodeImage(text,200,200,"C:\\Users\\hatip\\Desktop\\qr.png");
        } catch (Exception e) {
            System.out.println("Error when creating QR Code: " + e);
            return null;
        }*/
        return text;
    }
}

