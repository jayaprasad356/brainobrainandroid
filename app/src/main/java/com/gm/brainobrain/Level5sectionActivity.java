package com.gm.brainobrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gm.brainobrain.activities.PractisesActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;

public class Level5sectionActivity extends AppCompatActivity {

    LinearLayout lldoubleMutiple,llTripleMultiple,llDoubleDivision,llTribleDivision;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5section);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level5sectionActivity.this, PractisesActivity.class);
                startActivity(intent);


            }
        });

        lldoubleMutiple = findViewById(R.id.lldoubleMutiple);
        llTripleMultiple = findViewById(R.id.llTripleMultiple);
        llDoubleDivision = findViewById(R.id.llDoubleDivision);
        llTribleDivision = findViewById(R.id.llTribleDivision);


        lldoubleMutiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showbottomsheet1();
            }


        });
        llTripleMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showbottomsheet2();
            }


        });
        llDoubleDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showbottomsheet3();
            }


        });
        llTribleDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showbottomsheet4();
            }


        });
    }


    private void showbottomsheet1() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_lyt);
        TextView tvStartvisual = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvStartOral = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvOralView = bottomSheetDialog.findViewById(R.id.tvOralView);
        TextView tvSec = bottomSheetDialog.findViewById(R.id.tvSec);
        TextView tvVisualview = bottomSheetDialog.findViewById(R.id.tvVisualview);
        Slider sliderTime = bottomSheetDialog.findViewById(R.id.sliderTime);
        LinearLayout lltimeinsec = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlWomanoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlManoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);


        sliderTime.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = sliderTime.getValue();
                int i =  (int) value;


                Log.d("value =", String.valueOf(value));

                tvSec.setText(  i+" second");





            }
        });



        LinearLayout llOralvisible = bottomSheetDialog.findViewById(R.id.llOralvisible);
        tvStartvisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,NumbertypemutipleActivity.class);
                startActivity(intent);

            }
        });
        tvStartOral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,OraltypeActivity.class);
                startActivity(intent);

            }
        });
        tvOralView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.VISIBLE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.GONE);
                tvStartOral.setVisibility(View.VISIBLE);
                tvVisualview.setBackgroundResource(R.drawable.border13);
                tvOralView.setBackgroundResource(R.drawable.border5);


            }
        });
        tvVisualview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.GONE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.VISIBLE);
                tvStartOral.setVisibility(View.GONE);
                tvOralView.setBackgroundResource(R.drawable.border13);
                tvVisualview.setBackgroundResource(R.drawable.border5);

            }
        });


        rlManoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlWomanoral.setBackgroundResource(R.drawable.border12);
                rlManoral.setBackgroundResource(R.drawable.border4);


            }
        });
        rlWomanoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlManoral.setBackgroundResource(R.drawable.border12);
                rlWomanoral.setBackgroundResource(R.drawable.border4);


            }
        });

        bottomSheetDialog.show();

    }
    private void showbottomsheet2() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_lyt);
        TextView tvStartvisual = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvStartOral = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvOralView = bottomSheetDialog.findViewById(R.id.tvOralView);
        TextView tvSec = bottomSheetDialog.findViewById(R.id.tvSec);
        TextView tvVisualview = bottomSheetDialog.findViewById(R.id.tvVisualview);
        Slider sliderTime = bottomSheetDialog.findViewById(R.id.sliderTime);
        LinearLayout lltimeinsec = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlWomanoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlManoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);


        sliderTime.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = sliderTime.getValue();
                int i =  (int) value;


                Log.d("value =", String.valueOf(value));

                tvSec.setText(  i+" second");





            }
        });



        LinearLayout llOralvisible = bottomSheetDialog.findViewById(R.id.llOralvisible);
        tvStartvisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,NumbertypemutipleActivity.class);
                startActivity(intent);

            }
        });
        tvStartOral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,NumbertypemutipleActivity.class);
                startActivity(intent);

            }
        });
        tvOralView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.VISIBLE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.GONE);
                tvStartOral.setVisibility(View.VISIBLE);
                tvVisualview.setBackgroundResource(R.drawable.border13);
                tvOralView.setBackgroundResource(R.drawable.border5);


            }
        });
        tvVisualview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.GONE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.VISIBLE);
                tvStartOral.setVisibility(View.GONE);
                tvOralView.setBackgroundResource(R.drawable.border13);
                tvVisualview.setBackgroundResource(R.drawable.border5);

            }
        });


        rlManoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlWomanoral.setBackgroundResource(R.drawable.border12);
                rlManoral.setBackgroundResource(R.drawable.border4);


            }
        });
        rlWomanoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlManoral.setBackgroundResource(R.drawable.border12);
                rlWomanoral.setBackgroundResource(R.drawable.border4);


            }
        });

        bottomSheetDialog.show();

    }
    private void showbottomsheet3() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_lyt);
        TextView tvStartvisual = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvStartOral = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvOralView = bottomSheetDialog.findViewById(R.id.tvOralView);
        TextView tvSec = bottomSheetDialog.findViewById(R.id.tvSec);
        TextView tvVisualview = bottomSheetDialog.findViewById(R.id.tvVisualview);
        Slider sliderTime = bottomSheetDialog.findViewById(R.id.sliderTime);
        LinearLayout lltimeinsec = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlWomanoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlManoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);


        sliderTime.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = sliderTime.getValue();
                int i =  (int) value;


                Log.d("value =", String.valueOf(value));

                tvSec.setText(  i+" second");





            }
        });



        LinearLayout llOralvisible = bottomSheetDialog.findViewById(R.id.llOralvisible);
        tvStartvisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,NumbertypeDivideActivity.class);
                startActivity(intent);

            }
        });
        tvStartOral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,NumbertypeDivideActivity.class);
                startActivity(intent);

            }
        });
        tvOralView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.VISIBLE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.GONE);
                tvStartOral.setVisibility(View.VISIBLE);
                tvVisualview.setBackgroundResource(R.drawable.border13);
                tvOralView.setBackgroundResource(R.drawable.border5);


            }
        });
        tvVisualview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.GONE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.VISIBLE);
                tvStartOral.setVisibility(View.GONE);
                tvOralView.setBackgroundResource(R.drawable.border13);
                tvVisualview.setBackgroundResource(R.drawable.border5);

            }
        });


        rlManoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlWomanoral.setBackgroundResource(R.drawable.border12);
                rlManoral.setBackgroundResource(R.drawable.border4);


            }
        });
        rlWomanoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlManoral.setBackgroundResource(R.drawable.border12);
                rlWomanoral.setBackgroundResource(R.drawable.border4);


            }
        });

        bottomSheetDialog.show();

    }
    private void showbottomsheet4() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_lyt);
        TextView tvStartvisual = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvStartOral = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvOralView = bottomSheetDialog.findViewById(R.id.tvOralView);
        TextView tvSec = bottomSheetDialog.findViewById(R.id.tvSec);
        TextView tvVisualview = bottomSheetDialog.findViewById(R.id.tvVisualview);
        Slider sliderTime = bottomSheetDialog.findViewById(R.id.sliderTime);
        LinearLayout lltimeinsec = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlWomanoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        RelativeLayout rlManoral = bottomSheetDialog.findViewById(R.id.lltimeinsec);


        sliderTime.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = sliderTime.getValue();
                int i =  (int) value;


                Log.d("value =", String.valueOf(value));

                tvSec.setText(  i+" second");





            }
        });



        LinearLayout llOralvisible = bottomSheetDialog.findViewById(R.id.llOralvisible);
        tvStartvisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,NumbertypeDivideActivity.class);
                startActivity(intent);

            }
        });
        tvStartOral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level5sectionActivity.this,NumbertypeDivideActivity.class);
                startActivity(intent);

            }
        });
        tvOralView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.VISIBLE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.GONE);
                tvStartOral.setVisibility(View.VISIBLE);
                tvVisualview.setBackgroundResource(R.drawable.border13);
                tvOralView.setBackgroundResource(R.drawable.border5);


            }
        });
        tvVisualview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOralvisible.setVisibility(View.GONE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvStartvisual.setVisibility(View.VISIBLE);
                tvStartOral.setVisibility(View.GONE);
                tvOralView.setBackgroundResource(R.drawable.border13);
                tvVisualview.setBackgroundResource(R.drawable.border5);

            }
        });


        rlManoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlWomanoral.setBackgroundResource(R.drawable.border12);
                rlManoral.setBackgroundResource(R.drawable.border4);


            }
        });
        rlWomanoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlManoral.setBackgroundResource(R.drawable.border12);
                rlWomanoral.setBackgroundResource(R.drawable.border4);


            }
        });

        bottomSheetDialog.show();

    }

}