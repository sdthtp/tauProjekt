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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class OdemeFragment extends Fragment implements View.OnClickListener {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;
    String response;
    boolean processingPayment = false; //Determines whether there is a payment running (to prevent overload)



    public OdemeFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_odeme, container, false);
        ((Button) myView.findViewById(R.id.bezahlenBtn)).setOnClickListener(this);
        ProgressBar progressBar = myView.findViewById(R.id.pb_odeme);
        progressBar.setProgress(0);

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
        Fragment myFragment = getFragmentManager().findFragmentByTag("ODEME_FRAGMENT");
            @Override
            public void onTick(long millisUntilFinished) {
               if (myFragment != null && myFragment.isVisible()) {
                   i++;
                   mProgressBar.setProgress(i * 100 / (30000 / 1000));
                   try {
                       String check;
                       check = new NetworkHandling().execute("isPaid", response, LoginActivity.token).get();
                       System.out.println(check);
                       if (check.equals("paid successfully")) {
                           mProgressBar.setProgress(0);
                           mCountDownTimer.cancel();
                           Toast.makeText(getView().getContext(),getString(R.string.odeme_basarili), Toast.LENGTH_SHORT).show();
                           ImageView img = getView().getRootView().findViewById(R.id.imageView2);
                           processingPayment = false;
                           img.setVisibility(getView().INVISIBLE);
                           i = 0;
                       }
                   } catch (Exception e) {
                       processingPayment = false;
                       System.out.println("Error while trying to check whether Payment was complete! " + e);
                   }
               } else {
                   mProgressBar.setProgress(0);
                   mCountDownTimer.cancel();
                   i = 0;
                   processingPayment = false;
               }
            }

            @Override
            public void onFinish() {
                Toast.makeText(getView().getContext(),getString(R.string.odeme_zamaninda_yapilmadi),Toast.LENGTH_SHORT ).show();
                mProgressBar.setProgress(0);
                processingPayment = false;
                ImageView img = getView().getRootView().findViewById(R.id.imageView2);
                img.setVisibility(getView().INVISIBLE);
                i=0;
            }
        };
        mCountDownTimer.start();
    }

    public void onClick(View v) {
            if (!processingPayment) {
                try {
                    processingPayment = true;
                    response = new NetworkHandling().execute("requestQRCode", LoginActivity.token).get();
                    startTimer(getView());
                    ImageView img =  v.getRootView().findViewById(R.id.imageView2);
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = multiFormatWriter.encode(response, BarcodeFormat.QR_CODE,300,300);
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder();
                    Bitmap bitmpap = qrCodeEncoder.createBitmap(bitMatrix);
                    img.setImageBitmap(bitmpap);
                    img.setVisibility(v.VISIBLE);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                Toast.makeText(getView().getContext(),getString(R.string.lutfen_yeni_qr_code_istemeyin),Toast.LENGTH_SHORT ).show();
            }

    }
}

