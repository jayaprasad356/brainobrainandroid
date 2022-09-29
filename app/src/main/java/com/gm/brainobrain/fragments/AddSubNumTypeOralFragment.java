package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
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

import com.developer.kalert.KAlertDialog;
import com.gm.brainobrain.R;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.adapter.QuestionAdapter;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.Question;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class AddSubNumTypeOralFragment extends Fragment {
    Activity activity;
    TextView tvTimer;
    String seconds;
    int question;
    LinearProgressIndicator quesProgress;
    TextView tvQuestion,tvNumber;
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
    int countcomp = 0;

    int getQuestionLength = 0;
    CircularProgressIndicator cpbTime;


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
        cpbTime = root.findViewById(R.id.cpbTime);
        session = new Session(activity);
        seconds = session.getData(Constant.SECONDS);
        Level = session.getData(Constant.LEVEL);
        Title = session.getData(Constant.TYPE);
        session.setData(Constant.FRAG_LOCATE,Constant.EVENT_FRAG);
        session.setData(Constant.SCORE,"0");
        question = getArguments().getInt("QUESTION");
        cpbTime.setMax(Integer.parseInt(seconds));
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
                Log.d("BACKBUTTON", "Back button clicks");
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        Set<String> a=new HashSet<>();
        a.add("male");
        Voice v = new Voice("hi-in-x-hie-local",new Locale("hi_IN"),400,200,false,a);
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
        tts.setVoice(v);
        tts.setEngineByPackageName("com.google.android.tts");
        tvTimer.setText(seconds);
        tvQuestion.setText("Question "+question+" of 10");
        PractisesActivity.imgHome.setVisibility(View.INVISIBLE);
        ((PractisesActivity) requireActivity()).startTimer();

        PractisesActivity.tilte.setText(Html.fromHtml( "Practises>"+Level+"><b>"+Title+"</b>"));

        quesProgress.setProgress(question);

        noOfSeconds = Integer.parseInt(seconds) * 1000;

        countdownTime();

        return root;
    }


    private void speakNumber(String number)
    {
        boolean completed = false;

        new CountDownTimer(noOfSeconds,2000) {
            @Override
            public void onTick(long l) {
                countcomp = countcomp+1;
                Log.d("SPEAKCOUNTD",countcomp+"");
                if (countcomp < 3){
                    tts.speak(number,TextToSpeech.QUEUE_FLUSH,null); //speak after 1000ms

                }

            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    private void countdownTime() {
        Number = getQuestion();
        getQuestionLength = getQuestion().length();
        tvNumber.setText(Number);
        Random r = new Random();
        int CustomColors = Color.argb(225 ,r.nextInt(256),r.nextInt(256),r.nextInt(256));
        tvNumber.setTextColor(CustomColors);
        if (ttsspeak){
            tts.speak(Number.replaceAll("-","less ").replaceAll("x","multiplied by").replaceAll("/","divided by"),TextToSpeech.QUEUE_FLUSH,null);
        }
        else {
            speakNumber(Number.replaceAll("-","less ").replaceAll("x","multiplied by").replaceAll("/","divided by"));
        }
        tvQuestion.setText("Question "+question+" of 10");
        quesProgress.setProgress(question);


        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 +"");
                cpbTime.setProgress(Integer.parseInt(""+millisUntilFinished / 1000));
            }

            public void onFinish() {
                ttsspeak = true;
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
        Log.d("ORALTYPE",session.getData(Constant.QUESTION_ARRAY));
        String Question = "";
        try {
            JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
            if (jsonObject.getBoolean(Constant.SUCCESS))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                JSONArray jsonArray1 = jsonArray.getJSONObject(qno).getJSONArray(Constant.QUESTION);
                JSONArray jsonArray2 = jsonArray.getJSONObject(qno).getJSONArray(Constant.ANSWERS);
                actanswer = jsonArray2.get(0).toString();
                if (session.getData(Constant.TYPE).equals("Multiplication") || session.getData(Constant.TYPE).equals("Division"))
                {
                    ArrayList<String> questions = new ArrayList<>();

                    for (int i = 0; i < jsonArray1.length(); i++) {
                        questions.add(jsonArray1.get(i).toString());
                    }
                    String symbol = "";
                    if (session.getData(Constant.TYPE).equals("Multiplication")){
                        symbol = " x ";
                    }else if (session.getData(Constant.TYPE).equals("Division")){
                        symbol = " / ";

                    }
                    String s = "";
                    for (int i = 0; i < questions.size(); i++) {
                        if (i != questions.size() - 1){
                            s += questions.get(i) + symbol;
                        }
                        else {
                            s += questions.get(i) + "";

                        }

                    }
                    Question = s;
                    nextquestion = true;
                    qpno = 0;
                    qno = qno + 1;

                }else {

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
                if(etAnswer.getText().toString().trim().isEmpty()) {
                    ShowAlertDialog();
                }else if(etAnswer.getText().toString().trim().equals(".")) {
                    ShowAlertDialog();
                }else {
                    dialog.dismiss();
                    if (!etAnswer.getText().toString().trim().equals("")){
                        if (actanswer.equals(etAnswer.getText().toString().trim())){
                            score = score + 1;
                            session.setData(Constant.SCORE,score +"");
                        }

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
                        countdownTime();

                    }


                }




            }
        });

        dialog.show();
    }
    private void ShowAlertDialog() {
        new KAlertDialog(requireActivity(), KAlertDialog.WARNING_TYPE, 0)
                .setTitleText("oops")
                .setContentText("Enter your answer")
                .setConfirmText("ok")
                .show();
    }
}