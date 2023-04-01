package com.app.brainobrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.brainobrain.R;
import com.app.brainobrain.fragments.PractiseSectionFragment;
import com.app.brainobrain.fragments.PractisesLevelFragment;
import com.app.brainobrain.helper.Constant;
import com.app.brainobrain.helper.Session;

import java.util.Locale;

public class PractisesActivity extends AppCompatActivity {
    public static FragmentManager fm = null;
    Fragment practiseslevelFragment;
    public static TextView tilte,tvTimer;
    public static ImageView imgBack,imgHome;
    Activity activity;
    public static CountDownTimer cTimer = null;
    Session session;
    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    private boolean wasRunning;
    final Handler handler
            = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practises);
        activity = PractisesActivity.this;
        session = new Session(activity);
        tilte = findViewById(R.id.tilte);
        tvTimer = findViewById(R.id.tvTimer);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);


        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.getData(Constant.FRAG_LOCATE).equals(Constant.PRACTICE_FRAG)){
                    Intent intent = new Intent(PractisesActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();

                }else if (session.getData(Constant.FRAG_LOCATE).equals(Constant.SECTION_FRAG)){
                    redirectToPractiseFragment();

                }else if (session.getData(Constant.FRAG_LOCATE).equals(Constant.EVENT_FRAG)){
                    redirectToEventFragment();

                }
                //onBackPressed();
            }


        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PractisesActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        redirectToPractiseFragment();

    }
    private void runTimer()
    {



        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%02d:%02d",
                                minutes, secs);

                // Set the text view text.
                tvTimer.setText(time);
                session.setData(Constant.TIME_TAKEN,time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }
    private void redirectToEventFragment()
    {
        imgHome.setVisibility(View.VISIBLE);
        cancelTimer();
        resetTimer();
        if (session.getData(Constant.LEVEL_NAME).equals("Level 10")){
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ID, session.getData(Constant.LEVEL_ID));
            PractiseSectionFragment practiseSectionFragment = new PractiseSectionFragment();
            practiseSectionFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.container, practiseSectionFragment).commit();

        }else {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ID, session.getData(Constant.LEVEL_ID));
            PractiseSectionFragment practiseSectionFragment = new PractiseSectionFragment();
            practiseSectionFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.container, practiseSectionFragment).commit();
        }


    }

    private void redirectToPractiseFragment() {
        fm = getSupportFragmentManager();

        practiseslevelFragment = new PractisesLevelFragment();
        fm.beginTransaction().replace(R.id.container, practiseslevelFragment).commit();
        tilte.setText(Html.fromHtml( "Practises><b>Levels</b> "));
        cancelTimer();
    }

    public void cancelTimer()
    {
        if (cTimer != null) {
            cTimer.cancel();

        }
    }
    public void startTimer(){
        tvTimer.setVisibility(View.VISIBLE);
        seconds = 0;
        running = true;
        runTimer();
    }
    public void resetTimer(){
        tvTimer.setVisibility(View.GONE);
        running = false;
        seconds = 0;
        runTimer();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        }else {
//            FlashCardsQuestionVisualFragment flashCardsQuestionVisualFragment = (FlashCardsQuestionVisualFragment) getSupportFragmentManager().findFragmentByTag(Constant.FLASHCARDQUESTIONFRAGMENT);
//            AddSubNumTypeVisualFragment addSubNumTypeVisualFragment = (AddSubNumTypeVisualFragment) getSupportFragmentManager().findFragmentByTag(Constant.ADDSUBNUMTYPEVISUALFRAGMENT);
//            AddSubNumTypeOralFragment addSubNumTypeOralFragment = (AddSubNumTypeOralFragment) getSupportFragmentManager().findFragmentByTag(Constant.ADDSUBNUMTYPEORALFRAGMENT);
//            if ((flashCardsQuestionVisualFragment != null && flashCardsQuestionVisualFragment.isVisible())) {
//                cancelTimer();
//                imgHome.setVisibility(View.VISIBLE);
//                ResultFragment resultFragment = new ResultFragment();
//                PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();
//            }
//            else if ((addSubNumTypeVisualFragment != null && addSubNumTypeVisualFragment.isVisible())) {
//                cancelTimer();
//                imgHome.setVisibility(View.VISIBLE);
//                ResultFragment resultFragment = new ResultFragment();
//                PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();
//            }
//            else if ((addSubNumTypeOralFragment != null && addSubNumTypeOralFragment.isVisible())) {
//                cancelTimer();
//                imgHome.setVisibility(View.VISIBLE);
//                ResultFragment resultFragment = new ResultFragment();
//                PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();
//            }
//            else{
//                super.onBackPressed();
//
//            }
//        }
    }


}