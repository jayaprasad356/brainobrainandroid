package com.gm.brainobrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;

public class FlashcardsActivity extends AppCompatActivity {

    LinearLayout llFlashcards;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlashcardsActivity.this,ProgressActivity.class);
                startActivity(intent);


            }
        });


        llFlashcards = findViewById(R.id.llFlashcards);

        llFlashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbottomsheet();
            }
        });



    }

    private void showbottomsheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.flashcards_bottomsheet);

        TextView tvStartvisual = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvSec = bottomSheetDialog.findViewById(R.id.tvSec);
        Slider sliderTime = bottomSheetDialog.findViewById(R.id.sliderTime);




        tvStartvisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sec = tvSec.getText().toString();

                Intent intent = new Intent(FlashcardsActivity.this,VisualActivity.class);
                intent.putExtra("SECONDS",sec);
                startActivity(intent);

            }
        });


        sliderTime.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = sliderTime.getValue();
                int i =  (int) value;


                Log.d("value =", String.valueOf(value));

                tvSec.setText(""+i);





            }
        });


        bottomSheetDialog.show();

    }
}