package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.gm.brainobrain.R;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.adapter.QuestionAdapter;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.Number;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddSubNumTypeVisualFragment extends Fragment {
    RecyclerView recyclerView;
    Activity activity;
    TextView tvTimer;
    String Seconds;
    QuestionAdapter questionAdapter;
    String seconds;
    int question;
    LinearProgressIndicator quesProgress;
    TextView tvQuestion;
    private  boolean running;
    private int s = 0;
    Session session;
    RelativeLayout rl;
    String Level,Title,Number;
    EditText etAnswer;
    Button btnSubmit;
    TextView tvQuestionContent;
    Boolean nextquestion = false;
    int score = 0 ;
    int noOfSeconds;
    String actanswer;
    CircularProgressIndicator cpbTime;
    public AddSubNumTypeVisualFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_add_sub_num_type_visual, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        activity = getActivity();
        rl = root.findViewById(R.id.rl);
        quesProgress = root.findViewById(R.id.quesProgress);
        tvQuestion = root.findViewById(R.id.tvQuestion);
        etAnswer = root.findViewById(R.id.etAnswer);
        btnSubmit = root.findViewById(R.id.btnSubmit);
        tvQuestionContent = root.findViewById(R.id.tvQuestionContent);
        cpbTime = root.findViewById(R.id.cpbTime);
        session = new Session(activity);
        Level = session.getData(Constant.LEVEL);
        Title = session.getData(Constant.TYPE);
        tvTimer = root.findViewById(R.id.tvTimer);
        question = getArguments().getInt("QUESTION");
        session.setData(Constant.SCORE,"0");
        session.setData(Constant.FRAG_LOCATE,Constant.EVENT_FRAG);
        tvQuestion.setText("Question "+question+" of 10");
        ((PractisesActivity) requireActivity()).startTimer();
        seconds = "20";
        noOfSeconds = Integer.parseInt(seconds) * 1000;
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
                Log.d("BACKBUTTON", "Back button clicks");
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        PractisesActivity.imgHome.setVisibility(View.INVISIBLE);
        PractisesActivity.tilte.setText(Html.fromHtml( "Practises>"+Level+"><b>"+Title+"</b>"));
        quesProgress.setProgress(question);
        int noOfSeconds = Integer.parseInt(seconds) * 1000;
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,4);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5, GridLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(new GridLayoutManager(activity, 5, LinearLayoutManager.VERTICAL, false));

        recyclerView.setLayoutManager(gridLayoutManager);
        numberList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        tvTimer.setText(seconds);
        setTvCountdoun();
        return root;
    }


    private void nextQuestion()
    {
        ((PractisesActivity) requireActivity()).cancelTimer();
        numberList();
        tvQuestion.setText("Question "+question+" of 10");
        quesProgress.setProgress(question);
        int noOfSeconds = Integer.parseInt(seconds) * 1000;
        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + "");
                cpbTime.setProgress(Integer.parseInt(""+millisUntilFinished / 1000));
            }
            public void onFinish() {
                //tvTimer.setText("Time out");
                next();
            }
        }.start();
    }

    private void next()
    {
       // ((PractisesActivity) requireActivity()).cancelTimer();
        if (etAnswer.getText().toString().trim().equals("")){
            ShowAlertDialog();
        }else{
            if (actanswer.equals(etAnswer.getText().toString().trim())){
                score = score + 1;
                session.setData(Constant.SCORE,score +"");
            }
            if (question == 10){
                ((PractisesActivity) requireActivity()).resetTimer();
                PractisesActivity.imgHome.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putString("SECONDS", seconds);
                ResultFragment resultFragment = new ResultFragment();
                resultFragment.setArguments(bundle);
                PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();
            }else {
                question = question + 1;
                etAnswer.getText().clear();
                nextQuestion();
            }
        }
    }

    private void ShowAlertDialog() {
        new KAlertDialog(requireActivity(), KAlertDialog.WARNING_TYPE)
                .setTitleText("oops")
                .setContentText("Enter your answer")
                .setConfirmText("ok")
                .show();
    }


    private String numberList() {

        String Question = "";
        try {
            JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
            Log.d("ADDSUB_RES",session.getData(Constant.QUESTION_ARRAY));
            if (jsonObject.getBoolean(Constant.SUCCESS))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                JSONArray jsonArray1 = jsonArray.getJSONObject(question - 1).getJSONArray(Constant.QUESTION);
                JSONArray jsonArray2 = jsonArray.getJSONObject(question - 1).getJSONArray(Constant.ANSWERS);
                actanswer = jsonArray2.get(0).toString();
                ArrayList<String> questions = new ArrayList<>();

                for (int i = 0; i < jsonArray1.length(); i++) {
                    questions.add(jsonArray1.get(i).toString());
                }
                if (session.getData(Constant.TYPE).equals("Addition & Subtraction")) {
                    recyclerView.setVisibility(View.VISIBLE);
                    questionAdapter = new QuestionAdapter(activity, questions);
                    recyclerView.setAdapter(questionAdapter);
                } else {
                    String symbol = "";
                    if (session.getData(Constant.TYPE).equals("Multiplication")) {
                        symbol = " x ";
                    } else if (session.getData(Constant.TYPE).equals("Division")) {
                        symbol = " / ";

                    }
                    tvQuestionContent.setVisibility(View.VISIBLE);
                    String s = "";
                    for (int i = 0; i < questions.size(); i++) {
                        if (i != questions.size() - 1) {
                            s += questions.get(i) + symbol;
                            nextquestion = true;
                        } else {
                            s += questions.get(i) + "";
                            nextquestion = false;

                        }
                    }
                    tvQuestionContent.setText(s);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Question;
    }


    private void setTvCountdoun() {
        Number = numberList();
        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + "");
                cpbTime.setProgress(Integer.parseInt(""+millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                //tvTimer.setText("Time out");
                next();
            }
        }.start();
    }

}