package com.example.loginandsplashscreen.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnerFragment extends Fragment {
    private TextView ogrenciNoTextView;


    public OnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oner, container, false);
    }

    public void onClick_oner(View v){
        ogrenciNoTextView = (TextView) v.findViewById(R.id.ogrenciNoTextView);


        try {
            new NetworkHandling().execute("empfehlen", ogrenciNoTextView.getText().toString()).get();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
