package com.app.brainobrain.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.brainobrain.activities.PractisesActivity;
import com.app.brainobrain.R;
import com.app.brainobrain.helper.ApiConfig;
import com.app.brainobrain.helper.Constant;
import com.app.brainobrain.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultFragment extends Fragment {
    Button btnTryagain;
    Session session;
    Activity activity;
    TextView tvScore,tvFeedback,tvTimetaken;


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
        tvFeedback = root.findViewById(R.id.tvFeedback);
        tvTimetaken=root.findViewById(R.id.tvTimetaken);
        tvTimetaken.setText("Time Taken : "+session.getData(Constant.TIME_TAKEN));
        tvScore.setText(session.getData(Constant.SCORE) +" \n out of 10");
        tvFeedback.setText(getFeedback(session.getData(Constant.SCORE)));

        updateResult();
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
                    AddSubNumTypeVisualFragment.AddSubNumTypeOralFragment addSubNumTypeOralFragment = new AddSubNumTypeVisualFragment.AddSubNumTypeOralFragment();
                    addSubNumTypeOralFragment.setArguments(bundle);
                    PractisesActivity.fm.beginTransaction().replace(R.id.container, addSubNumTypeOralFragment,Constant.ADDSUBNUMTYPEORALFRAGMENT).commit();


                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("QUESTION", 1);
                    AddSubNumTypeVisualFragment.FlashCardsQuestionVisualFragment flashCardsQuestionVisualFragment = new AddSubNumTypeVisualFragment.FlashCardsQuestionVisualFragment();
                    flashCardsQuestionVisualFragment.setArguments(bundle);
                    PractisesActivity.fm.beginTransaction().replace(R.id.container, flashCardsQuestionVisualFragment, String.valueOf(R.layout.fragment_flash_cards_question_visual)).commit();
                }

            }
        });
        return root;
    }

    private void updateResult()
    {
        String currentString = session.getData(Constant.TIME_TAKEN);
        String[] separated = currentString.split(":");
        String avgsec =  ((Integer.parseInt(separated[0]) /2) + ":" +(Integer.parseInt(separated[1]) /2) + "");



        Map<String, String> params = new HashMap<>();
        params.put(Constant.TOKEN,session.getData(Constant.TOKEN));
        params.put(Constant.NUMBER_OF_QUESTIONS,  session.getData(Constant.NUMBER_OF_QUESTIONS));
        params.put(Constant.CORRECT_ANSWERS,session.getData(Constant.SCORE));
        params.put(Constant.TIME_TAKEN,"00:"+session.getData(Constant.TIME_TAKEN));
        params.put(Constant.AVERAGE_TIME_TAKEN,"00:"+avgsec);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("RESULT_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.PRACTICE_SECTION_STORE_STATS_URL(session.getData(Constant.LEVEL_ID),session.getData(Constant.SECTION_ID)), params, true,1);
    }

    private String getFeedback(String score)
    {
        String feedback = "";
        if (score.equals("9") || score.equals("10"))
        {
            feedback = "Excellent";

        }
        else if (score.equals("7") || score.equals("8"))
        {
            feedback = "Good";

        }
        else if (score.equals("5") || score.equals("6"))
        {
            feedback = "Need Practice";

        }
        else if (score.equals("3") || score.equals("4"))
        {
            feedback = "Need More Practice";

        }
        else
        {
            feedback = "Speak with your Teacher";

        }
        return feedback;
    }
}