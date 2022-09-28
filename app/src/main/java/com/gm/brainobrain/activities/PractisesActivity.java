package com.gm.brainobrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gm.brainobrain.R;
import com.gm.brainobrain.fragments.AddSubNumTypeOralFragment;
import com.gm.brainobrain.fragments.AddSubNumTypeVisualFragment;
import com.gm.brainobrain.fragments.FlashCardsQuestionVisualFragment;
import com.gm.brainobrain.fragments.PractisesLevelFragment;
import com.gm.brainobrain.fragments.ResultFragment;
import com.gm.brainobrain.helper.Constant;

public class PractisesActivity extends AppCompatActivity {
    public static FragmentManager fm = null;
    Fragment practiseslevelFragment;
    public static TextView tilte;
    public static ImageView imgBack,imgHome;
    Activity activity;
    public static CountDownTimer cTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practises);
        activity = PractisesActivity.this;
        tilte = findViewById(R.id.tilte);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }else {
            FlashCardsQuestionVisualFragment flashCardsQuestionVisualFragment = (FlashCardsQuestionVisualFragment) getSupportFragmentManager().findFragmentByTag(Constant.FLASHCARDQUESTIONFRAGMENT);
            AddSubNumTypeVisualFragment addSubNumTypeVisualFragment = (AddSubNumTypeVisualFragment) getSupportFragmentManager().findFragmentByTag(Constant.ADDSUBNUMTYPEVISUALFRAGMENT);
            AddSubNumTypeOralFragment addSubNumTypeOralFragment = (AddSubNumTypeOralFragment) getSupportFragmentManager().findFragmentByTag(Constant.ADDSUBNUMTYPEORALFRAGMENT);
            if ((flashCardsQuestionVisualFragment != null && flashCardsQuestionVisualFragment.isVisible())) {
                cancelTimer();
                imgHome.setVisibility(View.VISIBLE);
                ResultFragment resultFragment = new ResultFragment();
                PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();
            }
            else if ((addSubNumTypeVisualFragment != null && addSubNumTypeVisualFragment.isVisible())) {
                cancelTimer();
                imgHome.setVisibility(View.VISIBLE);
                ResultFragment resultFragment = new ResultFragment();
                PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();
            }
            else if ((addSubNumTypeOralFragment != null && addSubNumTypeOralFragment.isVisible())) {
                cancelTimer();
                imgHome.setVisibility(View.VISIBLE);
                ResultFragment resultFragment = new ResultFragment();
                PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();
            }
            else{
                super.onBackPressed();

            }
        }
    }


}