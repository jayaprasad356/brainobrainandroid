package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import java.util.Locale;

public class AddSubNumTypeOralFragment extends Fragment {
    Activity activity;
    TextView tvTimer;
    String seconds;
    int question;
    LinearProgressIndicator quesProgress;
    TextView tvQuestion,tvNumber,tvCountdoun;
    Session session;
    RelativeLayout rl;
    String Level,Title,Number;
    private  int s = 0;
    int noOfSeconds;
    TextToSpeech tts;
    boolean ttsspeak = false;
    int qno = 0,qpno = 0;
    boolean nextquestion = false;
    int score = 0 ;
    private  boolean running;
    String actanswer;


    public AddSubNumTypeOralFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_sub_num_type_oral, container, false);
        activity = getActivity();
        tvTimer = root.findViewById(R.id.tvTimer);
        tvNumber = root.findViewById(R.id.tvNumber);
        rl = root.findViewById(R.id.rl);
        quesProgress = root.findViewById(R.id.quesProgress);
        tvQuestion = root.findViewById(R.id.tvQuestion);
        session = new Session(activity);
        seconds = session.getData(Constant.SECONDS);
        Level = session.getData(Constant.LEVEL);
        Title = session.getData(Constant.TYPE);
        session.setData(Constant.SCORE,"0");
        question = getArguments().getInt("QUESTION");
        tts = new TextToSpeech(activity, i -> {
            if (i == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Lenguage not supported");
                }
            } else {
                Log.e("TTS", "Initialization failed");
            }

        });

        tvTimer.setText(seconds + " Sec");
        tvQuestion.setText("Question "+question+" of 10");
        PractisesActivity.imgBack.setVisibility(View.INVISIBLE);
        PractisesActivity.imgHome.setVisibility(View.INVISIBLE);

        PractisesActivity.tilte.setText(Html.fromHtml( "Practises>"+Level+"><b>"+Title+"</b>"));

        quesProgress.setProgress(question);

        noOfSeconds = Integer.parseInt(seconds) * 1000;

        countdownTime();

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


    private void speakNumber(String number)
    {
        new CountDownTimer(noOfSeconds,1000) {
            @Override
            public void onTick(long l) {
                tts.speak(number,TextToSpeech.QUEUE_FLUSH,null); //speak after 1000ms
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    private void countdownTime() {
        Number = getQuestion();

        tvNumber.setText(Number);
        if (ttsspeak){
            tts.speak(Number.replaceAll("-","less "),TextToSpeech.QUEUE_FLUSH,null);
        }
        else {
            speakNumber(Number.replaceAll("-","less "));

        }
        tvQuestion.setText("Question "+question+" of 10");
        quesProgress.setProgress(question);



        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + " Sec");
            }

            public void onFinish() {
                ttsspeak = true;
                tvTimer.setText("Time out");
                if (nextquestion){
                    showDialog();
                }
                else {
                    countdownTime();
                }

            }
        }.start();

    }


    private String getQuestion()
    {
        String Question = "";
        try {
            JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
            if (jsonObject.getBoolean(Constant.SUCCESS)){
                JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                JSONArray jsonArray1 = jsonArray.getJSONObject(qno).getJSONArray(Constant.QUESTION);
                JSONArray jsonArray2 = jsonArray.getJSONObject(qno).getJSONArray(Constant.ANSWERS);
                actanswer = jsonArray2.get(0).toString();

                Question = jsonArray1.get(qpno).toString();
                if (jsonArray1.length() - 1 == qpno){
                    nextquestion = true;
                    qpno = 0;
                    qno = qno + 1;
                }else {
                    nextquestion = false;
                    qpno = qpno + 1;
                }



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Question;

    }


    private void showDialog()
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.answer_custom_dialog);

        EditText etAnswer = (EditText) dialog.findViewById(R.id.etAnswer);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnSubmit);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etAnswer.getText().toString().trim().equals("")){
                    dialog.dismiss();
                    if (!etAnswer.getText().toString().trim().equals("")){
                        if (actanswer.equals(etAnswer.getText().toString().trim())){
                            score = score + 1;
                            session.setData(Constant.SCORE,score +"");
                        }

                    }
                    if (question == 10){
                        PractisesActivity.imgHome.setVisibility(View.VISIBLE);
                        Bundle bundle = new Bundle();
                        bundle.putString("SECONDS", seconds);
                        ResultFragment resultFragment = new ResultFragment();
                        resultFragment.setArguments(bundle);
                        PractisesActivity.fm.beginTransaction().add(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();

                    }else {
                        question = question + 1;
                        countdownTime();

                    }


                }


            }
        });

        dialog.show();
    }
}