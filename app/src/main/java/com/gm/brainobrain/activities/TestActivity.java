package com.gm.brainobrain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gm.brainobrain.R;

public class TestActivity extends AppCompatActivity {
    ImageView c1bdice1,c1sdice1,c1sdice2,c1sdice3,c1sdice4;
    ImageView c2bdice1,c2sdice1,c2sdice2,c2sdice3,c2sdice4;
    ImageView c3bdice1,c3sdice1,c3sdice2,c3sdice3,c3sdice4;
    ImageView c4bdice1,c4sdice1,c4sdice2,c4sdice3,c4sdice4;
    ImageView c5bdice1,c5sdice1,c5sdice2,c5sdice3,c5sdice4;
    LinearLayout lcolumn1,lcolumn2,lcolumn3,lcolumn4,lcolumn5;
    EditText etAnswer;
    Button btnSubmit;
    String first,second,third,fourth,fifth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        c1bdice1 = findViewById(R.id.c1bdice1);
        c1sdice1 = findViewById(R.id.c1sdice1);
        c1sdice2 = findViewById(R.id.c1sdice2);
        c1sdice3 = findViewById(R.id.c1sdice3);
        c1sdice4 = findViewById(R.id.c1sdice4);
        c2bdice1 = findViewById(R.id.c2bdice1);
        c2sdice1 = findViewById(R.id.c2sdice1);
        c2sdice2 = findViewById(R.id.c2sdice2);
        c2sdice3 = findViewById(R.id.c2sdice3);
        c2sdice4 = findViewById(R.id.c2sdice4);
        c3bdice1 = findViewById(R.id.c3bdice1);
        c3sdice1 = findViewById(R.id.c3sdice1);
        c3sdice2 = findViewById(R.id.c3sdice2);
        c3sdice3 = findViewById(R.id.c3sdice3);
        c3sdice4 = findViewById(R.id.c3sdice4);
        c4bdice1 = findViewById(R.id.c4bdice1);
        c4sdice1 = findViewById(R.id.c4sdice1);
        c4sdice2 = findViewById(R.id.c4sdice2);
        c4sdice3 = findViewById(R.id.c4sdice3);
        c4sdice4 = findViewById(R.id.c4sdice4);
        c5bdice1 = findViewById(R.id.c5bdice1);
        c5sdice1 = findViewById(R.id.c5sdice1);
        c5sdice2 = findViewById(R.id.c5sdice2);
        c5sdice3 = findViewById(R.id.c5sdice3);
        c5sdice4 = findViewById(R.id.c5sdice4);
        lcolumn1 = findViewById(R.id.lcolumn1);
        lcolumn2 = findViewById(R.id.lcolumn2);
        lcolumn3 = findViewById(R.id.lcolumn3);
        lcolumn4 = findViewById(R.id.lcolumn4);
        lcolumn5 = findViewById(R.id.lcolumn5);
        etAnswer = findViewById(R.id.etAnswer);
        btnSubmit = findViewById(R.id.btnSubmit);
        inVisibleAll();




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inVisibleAll();

                String answer = etAnswer.getText().toString().trim();


                if (!etAnswer.getText().toString().equals("") || etAnswer.getText().length() <= 5){
                    if (etAnswer.getText().length() == 1){
                        first=answer.substring(0,1);
                        column1(Integer.parseInt(first));

                    }
                    if (etAnswer.getText().length() == 2){
                        first=answer.substring(0,1);
                        second=answer.substring(1,2);
                        column1(Integer.parseInt(first));
                        column2(Integer.parseInt(second));

                    }
                    if (etAnswer.getText().length() == 3){
                        first=answer.substring(0,1);
                        second=answer.substring(1,2);
                        third=answer.substring(2,3);
                        column1(Integer.parseInt(first));
                        column2(Integer.parseInt(second));
                        column3(Integer.parseInt(third));

                    }
                    if (etAnswer.getText().length() == 4){
                        first=answer.substring(0,1);
                        second=answer.substring(1,2);
                        third=answer.substring(2,3);
                        fourth=answer.substring(3,4);
                        column1(Integer.parseInt(first));
                        column2(Integer.parseInt(second));
                        column3(Integer.parseInt(third));
                        column4(Integer.parseInt(fourth));

                    }
                    if (etAnswer.getText().length() == 5){
                        first=answer.substring(0,1);
                        second=answer.substring(1,2);
                        third=answer.substring(2,3);
                        fourth=answer.substring(3,4);
                        fifth=answer.substring(4,5);
                        column1(Integer.parseInt(first));
                        column2(Integer.parseInt(second));
                        column3(Integer.parseInt(third));
                        column4(Integer.parseInt(fourth));
                        column5(Integer.parseInt(fifth));

                    }

                }
            }
        });




    }

    private void inVisibleAll() {
        c1bdice1.setVisibility(View.INVISIBLE);
        c1sdice1.setVisibility(View.INVISIBLE);
        c1sdice2.setVisibility(View.INVISIBLE);
        c1sdice3.setVisibility(View.INVISIBLE);
        c1sdice4.setVisibility(View.INVISIBLE);
        c2bdice1.setVisibility(View.INVISIBLE);
        c2sdice1.setVisibility(View.INVISIBLE);
        c2sdice2.setVisibility(View.INVISIBLE);
        c2sdice3.setVisibility(View.INVISIBLE);
        c2sdice4.setVisibility(View.INVISIBLE);
        c3bdice1.setVisibility(View.INVISIBLE);
        c3sdice1.setVisibility(View.INVISIBLE);
        c3sdice2.setVisibility(View.INVISIBLE);
        c3sdice3.setVisibility(View.INVISIBLE);
        c3sdice4.setVisibility(View.INVISIBLE);
        c4bdice1.setVisibility(View.INVISIBLE);;
        c4sdice1.setVisibility(View.INVISIBLE);
        c4sdice2.setVisibility(View.INVISIBLE);
        c4sdice3.setVisibility(View.INVISIBLE);
        c4sdice4.setVisibility(View.INVISIBLE);
        c5bdice1.setVisibility(View.INVISIBLE);
        c5sdice1.setVisibility(View.INVISIBLE);
        c5sdice2.setVisibility(View.INVISIBLE);
        c5sdice3.setVisibility(View.INVISIBLE);
        c5sdice4.setVisibility(View.INVISIBLE);
        lcolumn1.setVisibility(View.INVISIBLE);
        lcolumn2.setVisibility(View.INVISIBLE);
        lcolumn3.setVisibility(View.INVISIBLE);
        lcolumn4.setVisibility(View.INVISIBLE);
        lcolumn5.setVisibility(View.INVISIBLE);
    }


    private void column1(int i)
    {
        lcolumn1.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c1sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c1sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c1sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c1sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c1bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column2(int i)
    {
        lcolumn2.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c2sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c2sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c2sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c2sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c2bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column3(int i)
    {
        lcolumn3.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c3sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c3sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c3sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c3sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c3bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column4(int i)
    {
        lcolumn4.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c4sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c4sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c4sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c4sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c4bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column5(int i) {
        lcolumn5.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c5sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c5sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c5sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c5sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c5bdice1.setVisibility(View.VISIBLE);
        }

    }
    public static boolean isBetween(int value, int min, int max)
    {
        return((value > min) && (value < max));
    }
}