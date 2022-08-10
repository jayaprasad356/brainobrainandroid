package com.gm.brainobrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Level10SectionActivity extends AppCompatActivity {

    LinearLayout lldoubling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level10_section);

        lldoubling = findViewById(R.id.lldoubling);

        lldoubling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level10SectionActivity.this,DoublingtypeActivity.class);
                startActivity(intent);
            }
        });
    }


}