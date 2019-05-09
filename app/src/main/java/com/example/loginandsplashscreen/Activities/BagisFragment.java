package com.example.loginandsplashscreen.Activities;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class BagisFragment extends Fragment implements View.OnClickListener {

    private TextView amounttextView;
    private Button bagis;
    private SegmentedButtonGroup sbg;
    private AlertDialog alertdialog;
    String[] type = new String[1];
    public BagisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_bagis, container, false);
        bagis = (Button) myView.findViewById(R.id.bu_bagis_onayla);
        bagis.setOnClickListener(this);
        type[0] = null;
        return myView;
    }

    public void onClick(View v){
        amounttextView = getView().findViewById(R.id.tf_bagis_amount);
        //ToggleButton t1 = getView().findViewById(R.id.tb_bagis_type);
        //String balanceId;


        sbg = getView().findViewById(R.id.sbg_bagis_type);

        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_bagis,null);
        alertbuilder.setView(view);
        alertdialog = alertbuilder.create();
        alertdialog.show();
        Button yes = view.findViewById(R.id.bagis_evet);
        Button no = view.findViewById(R.id.bagis_hayir);
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

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                try {
                    if (type[0] == null)
                        type[0] = "mensa";
                    System.out.println(type[0]);
                    System.out.println(new NetworkHandling().execute("freeItem", type[0], amounttextView.getText() + "", LoginActivity.token).get());
                    //TODO: implement correct responsehandling (e.g. if insufficient balance)
                    Toast.makeText(getView().getContext(), getString(R.string.bagisiniz_kabul_edilmistir),Toast.LENGTH_SHORT ).show();
                } catch (Exception e) {
                    System.out.println(e);
                }
                alertdialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view2) {
                Toast.makeText(getContext(),getString(R.string.bagis_iptal_edildi),Toast.LENGTH_SHORT).show();
                alertdialog.dismiss();
            }
        });

    }

}

