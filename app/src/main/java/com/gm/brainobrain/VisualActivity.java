package com.gm.brainobrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class VisualActivity extends AppCompatActivity {
    ImageView imgQuestion;
    TextView tvTimer,tvtimesec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);


        imgQuestion = findViewById(R.id.imgQuestion);
        imgQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualActivity.this,YourAnswerActivity.class);
                startActivity(intent);
            }
        });

        tvTimer = findViewById(R.id.tvTimer);
        tvtimesec = findViewById(R.id.tvtimesec);

        Intent intent = getIntent();
        String str = intent.getStringExtra("SECONDS");
        tvtimesec.setText(str);



        String getSeconds = tvtimesec.getText().toString().trim();
        int noOfSeconds = Integer.parseInt(getSeconds) * 1000;

        new CountDownTimer(noOfSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + " Second");
            }

            public void onFinish() {

                tvTimer.setText("Time out");
                Intent intent = new Intent(VisualActivity.this,YourAnswerActivity.class);
                startActivity(intent);


            }
        }.start();

    }
}