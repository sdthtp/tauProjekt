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
    String id;
    String amount;
    AlertDialog alertDialog;
    String[] type = new String[1];

    public ParagonderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO: make possible to insert partial sums (e.g. 0.5TL);
        View myView = inflater.inflate(R.layout.fragment_paragonder, container, false);
        gonder = (Button) myView.findViewById(R.id.bu_paragonder_send);
        gonder.setOnClickListener(this);
        type[0] = null;
        return myView;

    }

    public void onClick(View v) {

        TextView t = getView().findViewById(R.id.tf_paragonder_id);
        SegmentedButtonGroup sbg = getView().findViewById(R.id.sbg_sendmoney_type);

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

        id = t.getText() + "";
        t = getView().findViewById(R.id.tf_paragonder_amount);
        amount = t.getText() + "";
        View mview=getLayoutInflater().inflate(R.layout.dialog_paragonder, null);
        Button yes =mview.findViewById(R.id.paragonder_evet);
        Button no=mview.findViewById(R.id.paragonder_hayir);
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getContext());
        alertdialogbuilder.setView(mview);
        alertDialog = alertdialogbuilder.create();
        alertDialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (type[0]==null)
                        type[0]="mensa";
                    System.out.println(type[0]);
                    String response = new NetworkHandling().execute("transfer",id,type[0],amount,LoginActivity.token).get();
                    //TODO: Implement correct response to wrong input (e.g. if user does not exist or balance is insufficient)
                    System.out.println("TRANSFER DONE: RESPONSE: " + response);
                    Toast.makeText(getContext(),getString(R.string.para_gonderildi),Toast.LENGTH_SHORT ).show();
                    alertDialog.dismiss();
                    MainActivity.refresh();
                } catch (Exception e) {
                    System.out.println("EXCEPTION WHILE MONEY TRANSFER: " + e);
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),getString(R.string.para_gonderilmedi),Toast.LENGTH_SHORT ).show();
                alertDialog.dismiss();
            }
        });
    }


}
