package com.example.loginandsplashscreen.Activities;

import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.Handlers.QRCodeEncoder;
import com.example.loginandsplashscreen.JsonedClasses.Customer;
import com.example.loginandsplashscreen.R;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;


public class OdemeFragment extends Fragment implements View.OnClickListener {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;
    String response;



    public OdemeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_odeme, container, false);
        ((Button) myView.findViewById(R.id.bezahlenBtn)).setOnClickListener(this);

        Customer cst = null;
        Button bezahlen;

        TextView t = (TextView) myView.findViewById(R.id.bezahlenName);

        //bezahlen = (Button) myView.findViewById(R.id.bezahlenBtn);
        //bezahlen.setOnClickListener(this);
        return myView;
    }

    public void startTimer(View v) {
        mProgressBar=v.findViewById(R.id.pb_odeme);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(30000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress(i*100/(30000/1000));
                try {
                    String check;
                    check = new NetworkHandling().execute("isPaid",response,LoginActivity.token).get();
                    System.out.println(check);
                    if (check.equals("true")) {
                        mCountDownTimer.cancel();
                        mProgressBar.setProgress(100);
                        i=0;
                    }
                } catch (Exception e) {
                    System.out.println("Error while trying to check whether Payment was complete! " + e);
                }

            }

            @Override
            public void onFinish() {
                System.out.println("Payment could not be processed!");
                i++;
                mProgressBar.setProgress(100);
                i=0;
            }
        };
        mCountDownTimer.start();
    }

    public void onClick(View v) {
        try {
            response = new NetworkHandling().execute("requestQRCode", LoginActivity.token).get();
            startTimer(getView());
            ImageView img =  v.getRootView().findViewById(R.id.imageView2);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(response, BarcodeFormat.QR_CODE,300,300);
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder();
            Bitmap bitmpap = qrCodeEncoder.createBitmap(bitMatrix);
            img.setImageBitmap(bitmpap);
        } catch (Exception e) {
            System.out.println(e);
        }

        /*QRCodeFragment qrCodeFragment = new QRCodeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, qrCodeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/

    }
}

