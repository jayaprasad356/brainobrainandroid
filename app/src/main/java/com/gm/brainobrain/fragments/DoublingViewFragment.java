package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gm.brainobrain.R;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.adapter.DoublingNumberAdapter;
import com.gm.brainobrain.adapter.SectionAdapter;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.model.Number;
import com.gm.brainobrain.model.Section;

import java.util.ArrayList;

public class DoublingViewFragment extends Fragment {

    EditText etAnswer;
    Button btnStart,btnClose;
    RecyclerView recyclerView;
    Activity activity;
    ArrayList<Number> doublenumbers = new ArrayList<>();
    DoublingNumberAdapter doublingNumberAdapter;
    int lastnum = 0;
    TextView tvTimer,tvEntries;
    private  int seconds= 0;
    private  boolean running;
    int entries = 0;



    public DoublingViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_doubling_view, container, false);
        tvTimer = root.findViewById(R.id.tvTimer);
        recyclerView = root.findViewById(R.id.recyclerView);
        activity = getActivity();
        etAnswer = root.findViewById(R.id.etAnswer);
        btnStart = root.findViewById(R.id.btnStart);
        btnClose = root.findViewById(R.id.btnClose);
        tvEntries = root.findViewById(R.id.tvEntries);
        PractisesActivity.tilte.setText(Html.fromHtml( "Practises>Level 10><b>Doubling</b> "));

        etAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")){
                    int val = Integer.parseInt(s.toString().trim());
                    int valans = lastnum * 2;

                    if (valans == val){
                        entries = entries + 1;
                        tvEntries.setText(""+entries);
                        lastnum = Integer.parseInt(s.toString().trim());
                        Number number = new Number("1",etAnswer.getText().toString().trim());
                        doublenumbers.add(number);
                        recyclerView.scrollToPosition(doublenumbers.size() - 1);
                        doublingList();
                        etAnswer.getText().clear();

                    }

                }

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false));

        doublingList();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etAnswer.getText().toString().trim().equals("")){
                    entries = 1;
                    tvEntries.setText(""+entries);
                    btnClose.setVisibility(View.VISIBLE);
                    running = true;
                    startTimer();
                    lastnum = Integer.parseInt(etAnswer.getText().toString().trim());
                    Number number = new Number("1",etAnswer.getText().toString().trim());
                    doublenumbers.add(number);
                    doublingList();
                    btnStart.setVisibility(View.GONE);
                    etAnswer.getText().clear();



                }

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                Intent intent = new Intent(activity,PractisesActivity.class);
                startActivity(intent);

            }
        });

        return root;
    }

    private void doublingList()
    {
        doublingNumberAdapter = new DoublingNumberAdapter(activity, doublenumbers);
        recyclerView.setAdapter(doublingNumberAdapter);
    }
    private  void  startTimer(){



        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hrs = seconds/3600;
                int min = (seconds%36000)/60;
                int sec = seconds%60;
                String time = String.format("%02d:%02d:%02d",hrs,min,sec);
                tvTimer.setText(time);
                if (running){
                    seconds++;
                }
                handler.postDelayed(this,0);


            }
        });
    }

}