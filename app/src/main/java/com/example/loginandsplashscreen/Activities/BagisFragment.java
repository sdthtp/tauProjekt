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
public class BagisFragment extends Fragment {

    private TextView amounttextView;

    public BagisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bagis, container, false);
    }

    public void onClicked_FreeMeal(View v){
        amounttextView = (TextView) v.findViewById(R.id.amount);
        String token = LoginActivity.token;

        try {
            new NetworkHandling().execute("freeItem", amounttextView.getText().toString(), "mensa", token).get();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onClicked_FreeRide(View v){
        amounttextView = (TextView) v.findViewById(R.id.amount);
        String token = LoginActivity.token;

        try {
            new NetworkHandling().execute("freeItem", amounttextView.getText().toString(), "shuttle", token).get();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

