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

import co.ceryle.segmentedbutton.SegmentedButtonGroup;

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
        final String[] type = new String[1];
        SegmentedButtonGroup sbg = getView().findViewById(R.id.sbg_sendmoney_type);

        sbg.setPosition(0,30);
        sbg.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                if(position == 0){
                    type[0] = "mensa";
                }
                else if(position == 1){
                    type[0] = "shuttle";
                }
            }
        });

        String id = t.getText() + "";
        t = getView().findViewById(R.id.tf_paragonder_amount);
        String amount = t.getText() + "";

        try {
            String response = new NetworkHandling().execute("transfer",id,type[0],amount,LoginActivity.token).get();
            //TODO: Implement correct response to wrong input (e.g. if user does not exist or balance is insufficient)
            System.out.println("TRANSFER DONE: RESPONSE: " + response);
        } catch (Exception e) {
            System.out.println("EXCEPTION WHILE MONEY TRANSFER: " + e);
        }
        Toast.makeText(getView().getContext(), "Para gönderildi.",Toast.LENGTH_SHORT ).show();
    }


}
