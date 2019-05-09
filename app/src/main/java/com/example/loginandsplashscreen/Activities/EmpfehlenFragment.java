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

import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;

public class EmpfehlenFragment extends Fragment implements View.OnClickListener {
    private TextView ogrenciNoTextView;
    private Button oner;
    private AlertDialog alertDialog;


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
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View mview=getLayoutInflater().inflate(R.layout.dialog_empfehlen, null);
        Button button =(Button) mview.findViewById(R.id.oner_evet);
        Button button1=(Button) mview.findViewById(R.id.oner_hayir);
        builder.setView(mview);
        alertDialog = builder.create();
        alertDialog.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    System.out.println(new NetworkHandling().execute("empfehlen", ogrenciNoTextView.getText() + "").get());
                    //TODO: implement correct reponsehandling e.g. if student doesn't exist in database
                    Toast.makeText(getView().getContext(), getString(R.string.kisi_onerilmistir),Toast.LENGTH_SHORT ).show();
                    alertDialog.dismiss();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),getString(R.string.kisi_onerilmedi),Toast.LENGTH_SHORT ).show();
                alertDialog.dismiss();
            }
        });
    }



}
