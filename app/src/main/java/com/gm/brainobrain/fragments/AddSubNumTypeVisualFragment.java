package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gm.brainobrain.R;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.adapter.QuestionAdapter;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddSubNumTypeVisualFragment extends Fragment {
    RecyclerView recyclerView;
    Activity activity;
    QuestionAdapter questionAdapter;
    String seconds;
    int question;
    LinearProgressIndicator quesProgress;
    TextView tvQuestion,tvCountdoun;
    private  boolean running;
    private  int s = 0;
    Session session;
    RelativeLayout rl;
    String Level,Title;
    EditText etAnswer;
    Button btnSubmit;
    TextView tvQuestionContent;
    int score = 0 ;
    String actanswer;

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
        session = new Session(activity);
        seconds = session.getData(Constant.SECONDS);
        Level = session.getData(Constant.LEVEL);
        Title = session.getData(Constant.TYPE);
        question = getArguments().getInt("QUESTION");
        session.setData(Constant.SCORE,"0");
        tvQuestion.setText("Question "+question+" of 10");
        PractisesActivity.imgBack.setVisibility(View.INVISIBLE);
        PractisesActivity.imgHome.setVisibility(View.INVISIBLE);
        PractisesActivity.tilte.setText(Html.fromHtml( "Practises>"+Level+"><b>"+Title+"</b>"));
        quesProgress.setProgress(question);
        int noOfSeconds = Integer.parseInt(seconds) * 1000;





        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,4);
        recyclerView.setLayoutManager(gridLayoutManager);
        numberList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCountdoun = view.findViewById(R.id.tvCountdoun);
        running = true;
        startTimer();


    }
    private void startTimer() {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hrs = s/3600;
                int min = (s%36000)/60;
                int sec = s%60;
                String time = String.format("%02d:%02d:%02d",hrs,min,sec);
                tvCountdoun.setText(time);
                if (running){
                    s++;
                }
                handler.postDelayed(this,0);


            }
        });
    }


    private void nextQuestion()
    {
        numberList();

        tvQuestion.setText("Question "+question+" of 10");
        quesProgress.setProgress(question);

    }

    private void next()
    {
        if (!etAnswer.getText().toString().trim().equals("")){
            if (actanswer.equals(etAnswer.getText().toString().trim())){
                score = score + 1;
                session.setData(Constant.SCORE,score +"");
            }

        }

        ((PractisesActivity) requireActivity()).cancelTimer();
        if (question == 10){
            PractisesActivity.imgHome.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("SECONDS", seconds);
            ResultFragment resultFragment = new ResultFragment();
            resultFragment.setArguments(bundle);
            PractisesActivity.fm.beginTransaction().add(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();

        }else {
            question = question + 1;
            etAnswer.getText().clear();
            nextQuestion();
        }
    }



    private void numberList() {


        try {
            JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
            Log.d("ADDSUB_RES",session.getData(Constant.QUESTION_ARRAY));
            if (jsonObject.getBoolean(Constant.SUCCESS)){
                JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                JSONArray jsonArray1 = jsonArray.getJSONObject(question-1).getJSONArray(Constant.QUESTION);
                JSONArray jsonArray2 = jsonArray.getJSONObject(question-1).getJSONArray(Constant.ANSWERS);
                actanswer = jsonArray2.get(0).toString();
                ArrayList<String> questions = new ArrayList<>();

                for (int i = 0; i < jsonArray1.length(); i++) {
                    questions.add(jsonArray1.get(i).toString());
                }
                if (session.getData(Constant.TYPE).equals("Addition & Subtraction")){
                    recyclerView.setVisibility(View.VISIBLE);
                    questionAdapter = new QuestionAdapter(activity, questions);
                    recyclerView.setAdapter(questionAdapter);
                }
                else {
                    String symbol = "";
                    if (session.getData(Constant.TYPE).equals("Multiplication")){
                        symbol = " x ";
                    }else if (session.getData(Constant.TYPE).equals("Division")){
                        symbol = " / ";

                    }
                    tvQuestionContent.setVisibility(View.VISIBLE);
                    String s = "";
                    for (int i = 0; i < questions.size(); i++) {
                        if (i != questions.size() - 1){
                            s += questions.get(i) + symbol;
                        }
                        else {
                            s += questions.get(i) + "";

                        }

                    }
                    tvQuestionContent.setText(s);



                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}