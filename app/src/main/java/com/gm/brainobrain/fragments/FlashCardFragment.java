package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.R;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;

public class FlashCardFragment extends Fragment {
    LinearLayout llFlashcards;
    Activity activity;
    Session session;



    public FlashCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_flash_card, container, false);
        session = new Session(getActivity());
        llFlashcards = root.findViewById(R.id.llFlashcards);
        activity = getActivity();
        PractisesActivity.tilte.setText(Html.fromHtml( "Practises><b>Level 1</b> "));

        llFlashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbottomsheet();
            }
        });
        return root;
    }

    private void showbottomsheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.flashcards_bottomsheet);

        TextView tvStartvisual = bottomSheetDialog.findViewById(R.id.tvStartvisual);
        TextView tvSec = bottomSheetDialog.findViewById(R.id.tvSec);
        Slider sliderTime = bottomSheetDialog.findViewById(R.id.sliderTime);




        tvStartvisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                String sec = tvSec.getText().toString().trim();
                session.setData(Constant.SECONDS,sec);
                session.setData(Constant.QUES_TYPE,Constant.IMAGE);
                Bundle bundle = new Bundle();
                bundle.putInt("QUESTION", 1);
                FlashCardsQuestionVisualFragment flashCardsQuestionVisualFragment = new FlashCardsQuestionVisualFragment();
                flashCardsQuestionVisualFragment.setArguments(bundle);
                PractisesActivity.fm.beginTransaction().add(R.id.container, flashCardsQuestionVisualFragment, Constant.FLASHCARDQUESTIONFRAGMENT).commit();
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
                tvSec.setText(""+i);

            }
        });
        bottomSheetDialog.show();

    }


}