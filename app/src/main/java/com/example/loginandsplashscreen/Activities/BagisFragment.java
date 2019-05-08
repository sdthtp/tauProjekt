package com.example.loginandsplashscreen.Activities;


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
        return myView;
    }

    public void onClick(View v){
        amounttextView = getView().findViewById(R.id.tf_bagis_amount);
        String token = LoginActivity.token;
        //ToggleButton t1 = getView().findViewById(R.id.tb_bagis_type);
        //String balanceId;

        final String[] type = new String[1];
        SegmentedButtonGroup sbg = getView().findViewById(R.id.sbg_bagis_type);

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

        /*boolean k = t1.isChecked();
        if (!k) {
            balanceId = "mensa";
            System.out.println(balanceId);
        } else {
            balanceId = "shuttle";
            System.out.println(balanceId);
        }*/
        try {
            System.out.println(new NetworkHandling().execute("freeItem", type[0], amounttextView.getText() + "", token).get());
            //TODO: implement correct responsehandling (e.g. if insufficient balance)
            Toast.makeText(getView().getContext(), getString(R.string.bagisiniz_kabul_edilmistir),Toast.LENGTH_SHORT ).show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

