package com.example.loginandsplashscreen.Activities;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

public class ParagonderFragment extends Fragment implements OnClickListener {

    Button gonder;

    public ParagonderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO: make possible to insert partial sums (e.g. 0.5TL);
        //TODO: Tell backend to safe everything about cash as double/float
        View myView = inflater.inflate(R.layout.fragment_paragonder, container, false);
        gonder = (Button) myView.findViewById(R.id.bu_paragonder_send);
        gonder.setOnClickListener(this);
        return myView;
    }

    public void onClick(View v) {
        TextView t = getView().findViewById(R.id.tf_paragonder_id);
        String id = t.getText() + "";
        t = getView().findViewById(R.id.tf_paragonder_amount);
        String amount = t.getText() + "";
        ToggleButton t1 = getView().findViewById(R.id.tb_paragonder_balanceId);
        String balanceId = "";
        boolean k = t1.isChecked();
        if (!k) {
            balanceId = "mensa";
            System.out.println(balanceId);
        } else {
            balanceId = "shuttle";
            System.out.println(balanceId);
        }
        try {
            String response = new NetworkHandling().execute("transfer",id,balanceId,amount,LoginActivity.token).get();
            //TODO: Implement correct response to wrong input (e.g. if user does not exist or balance is insufficient)
            System.out.println("TRANSFER DONE: RESPONSE: " + response);
        } catch (Exception e) {
            System.out.println("EXCEPTION WHILE MONEY TRANSFER: " + e);
        }
        Toast.makeText(getView().getContext(), "Para gönderildi.",Toast.LENGTH_SHORT ).show();
    }


}
