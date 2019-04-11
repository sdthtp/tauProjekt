package com.example.loginandsplashscreen.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

public class EmpfehlenFragment extends Fragment implements View.OnClickListener {
    private TextView ogrenciNoTextView;
    private Button oner;


    public EmpfehlenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_empfehlen, container, false);
        oner = (Button) myView.findViewById(R.id.bu_empfehlen_onayla);
        oner.setOnClickListener(this);
        return myView;
    }

    public void onClick(View v){
        ogrenciNoTextView = getView().findViewById(R.id.tf_empfehlen_id);

        try {
            System.out.println(new NetworkHandling().execute("empfehlen", ogrenciNoTextView.getText() + "").get());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
