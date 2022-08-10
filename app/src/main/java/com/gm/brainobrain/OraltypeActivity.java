package com.gm.brainobrain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class OraltypeActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextView tvquestion;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oraltype);

        tvquestion = findViewById(R.id.tvquestion);


        tts = new TextToSpeech(this, this);
        new CountDownTimer(5000,2000) {
            @Override
            public void onTick(long l) {

                tts.speak(tvquestion.getText().toString(),TextToSpeech.QUEUE_FLUSH,null); //speak after 1000ms


            }

            @Override
            public void onFinish() {

            }
        }.start();
    }



    @Override
    public void onInit(int i) {

        if (i == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {

            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

}