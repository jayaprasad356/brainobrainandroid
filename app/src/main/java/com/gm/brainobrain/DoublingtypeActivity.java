package com.gm.brainobrain;

import androidx.appcompat.app.AppCompatActivity;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DoublingtypeActivity extends AppCompatActivity {

    private  int seconds= 0;
    private  boolean running;
    View keyboard;


    TextView tvTimer,tvAns;
    EditText edAns;
    Button btnStart,btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doublingtype);


        tvTimer = findViewById(R.id.tvTimer);
        tvAns = findViewById(R.id.tvAns);
        edAns = findViewById(R.id.edAns);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        keyboard = findViewById(R.id.keyboard);



        startTimer();

        edAns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tvAns.setText(s);

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });



    }
    public void  onstart(View view){

        btnStart.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
        keyboard.setVisibility(View.VISIBLE);
        running = true;


    }


    public void  onstop(View view){

        running = false;



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