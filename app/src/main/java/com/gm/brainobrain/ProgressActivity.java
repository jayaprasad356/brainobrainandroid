package com.gm.brainobrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ProgressActivity extends AppCompatActivity {

    LinearLayout llLevel1,llLevel2,llLevel3,llLevel5,llLevel10;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgressActivity.this,DashboardActivity.class);
                startActivity(intent);


            }
        });


        llLevel1 = findViewById(R.id.llLevel1);
        llLevel2 = findViewById(R.id.llLevel2);
        llLevel3 = findViewById(R.id.llLevel3);
        llLevel5 = findViewById(R.id.llLevel5);
        llLevel10 = findViewById(R.id.llLevel10);

        llLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this,FlashcardsActivity.class);
                startActivity(intent);
            }
        });
        llLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this,NumbertypeActivity.class);
                startActivity(intent);
            }
        });
        llLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this,Level3sectionActivity.class);
                startActivity(intent);
            }
        });
        llLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this,Level5sectionActivity.class);
                startActivity(intent);
            }
        });
        llLevel10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this,Level10SectionActivity.class);
                startActivity(intent);
            }
        });
    }
}