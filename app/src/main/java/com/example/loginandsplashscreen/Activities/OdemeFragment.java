package com.example.loginandsplashscreen.Activities;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.Handlers.QRCodeHandler;
import com.example.loginandsplashscreen.JsonedClasses.Customer;
import com.example.loginandsplashscreen.R;
import com.google.gson.Gson;

public class OdemeFragment extends Fragment implements View.OnClickListener {

    public OdemeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_odeme, container, false);
        ((Button) myView.findViewById(R.id.bezahlenBtn)).setOnClickListener(this);

        Customer cst = null;
        Button bezahlen;

        try {
            cst = new Gson().fromJson(new NetworkHandling().execute("getInfo",LoginActivity.token).get(), Customer.class);
        } catch (Exception e) {
            System.out.println(e);
        }


        TextView t = (TextView) myView.findViewById(R.id.bezahlenName);
        try {
            t.setText("İsim: " + cst.getName());
            t = (TextView) myView.findViewById(R.id.bezahlenID);
            t.setText("ID: " + cst.getId());
            t = (TextView) myView.findViewById(R.id.bezahlenMensaBakiye);
            t.setText("Mensa Bakiye: " + cst.getBalanceMensa());
            t = (TextView) myView.findViewById(R.id.bezahlenShuttleBakiye);
            t.setText("Shuttle Bakiye: " + cst.getBalanceShuttle());
        } catch (Exception e) {
            System.out.println(e);
        }

        //bezahlen = (Button) myView.findViewById(R.id.bezahlenBtn);
        //bezahlen.setOnClickListener(this);
        return myView;
    }

    public void onClick(View v) {
        Toast.makeText(getActivity(), "QR-Image generated!", Toast.LENGTH_LONG).show();

        try {
            String response = new NetworkHandling().execute("requestQRCode", LoginActivity.token).get();
            System.out.println(response);

            Bitmap bmp = new QRCodeHandler().generateQRCodeImage(response, 700,700);
            ImageView img =  v.getRootView().findViewById(R.id.imageView2);
            img.setImageBitmap(bmp);


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

