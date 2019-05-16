package com.example.loginandsplashscreen.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YemekListeFragment extends Fragment implements View.OnClickListener {

    //***********
    //Not used in final version
    //***********
    public YemekListeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_yemek_liste, container, false);
        try {
            String k = new NetworkHandling().execute("yemeklistesi").get();
            TextView v = myView.findViewById(R.id.textView3);
            v.setText(k);
            System.out.println("DONE!!!!!");

        } catch (Exception e) {
            System.out.println(e);
        }


        return myView;
    }

    @Override
    public void onClick(View v) {

    }
}
