package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.R;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;

public class ResultFragment extends Fragment {
    Button btnTryagain;
    Session session;
    Activity activity;
    TextView tvScore;


    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_result, container, false);
        activity = getActivity();
        session = new Session(activity);
        btnTryagain = root.findViewById(R.id.btnTryagain);
        tvScore = root.findViewById(R.id.tvScore);
        tvScore.setText(session.getData(Constant.SCORE) +" \n out of 10");
        btnTryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.getData(Constant.QUES_TYPE).equals(Constant.NUMBER)){
                    Bundle bundle = new Bundle();
                    bundle.putInt("QUESTION", 1);
                    AddSubNumTypeVisualFragment addSubNumTypeVisualFragment = new AddSubNumTypeVisualFragment();
                    addSubNumTypeVisualFragment.setArguments(bundle);
                    PractisesActivity.fm.beginTransaction().replace(R.id.container, addSubNumTypeVisualFragment, Constant.ADDSUBNUMTYPEVISUALFRAGMENT).commit();


                }
                else if (session.getData(Constant.QUES_TYPE).equals(Constant.ORAL_NUMBER)){
                    Bundle bundle = new Bundle();
                    bundle.putInt("QUESTION", 1);
                    bundle.putString("NUMBER", "5");
                    AddSubNumTypeOralFragment addSubNumTypeOralFragment = new AddSubNumTypeOralFragment();
                    addSubNumTypeOralFragment.setArguments(bundle);
                    PractisesActivity.fm.beginTransaction().add(R.id.container, addSubNumTypeOralFragment,Constant.ADDSUBNUMTYPEORALFRAGMENT).commit();


                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("QUESTION", 1);
                    FlashCardsQuestionVisualFragment flashCardsQuestionVisualFragment = new FlashCardsQuestionVisualFragment();
                    flashCardsQuestionVisualFragment.setArguments(bundle);
                    PractisesActivity.fm.beginTransaction().add(R.id.container, flashCardsQuestionVisualFragment, String.valueOf(R.layout.fragment_flash_cards_question_visual)).commit();
                }

            }
        });
        return root;
    }
}