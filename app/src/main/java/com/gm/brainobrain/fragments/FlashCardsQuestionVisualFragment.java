package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.kalert.KAlertDialog;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.R;
import com.gm.brainobrain.adapter.QuestionAdapter;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class FlashCardsQuestionVisualFragment extends Fragment {
    private  int s = 0;
    private  boolean running;
    TextView tvTimer,tvCountdoun;
    Activity activity;
    String seconds;
    int question;
    LinearProgressIndicator quesProgress;
    TextView tvQuestion;
    Session session;
    EditText etAnswer;
    ImageView c1bdice1,c1sdice1,c1sdice2,c1sdice3,c1sdice4;
    ImageView c2bdice1,c2sdice1,c2sdice2,c2sdice3,c2sdice4;
    ImageView c3bdice1,c3sdice1,c3sdice2,c3sdice3,c3sdice4;
    ImageView c4bdice1,c4sdice1,c4sdice2,c4sdice3,c4sdice4;
    ImageView c5bdice1,c5sdice1,c5sdice2,c5sdice3,c5sdice4;
    LinearLayout lcolumn1,lcolumn2,lcolumn3,lcolumn4,lcolumn5;
    String first,second,third,fourth,fifth;
    String Level,Title;
    int score = 0 ;
    String actanswer,name;


    public FlashCardsQuestionVisualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_flash_cards_question_visual, container, false);
        activity = getActivity();
        session = new Session(activity);

        tvTimer = root.findViewById(R.id.tvTimer);
        quesProgress = root.findViewById(R.id.quesProgress);
        tvQuestion = root.findViewById(R.id.tvQuestion);
        init(root);
        Level = session.getData(Constant.LEVEL);
        Title = session.getData(Constant.TYPE);
        seconds = session.getData(Constant.SECONDS);
        session.setData(Constant.SCORE,"0");
        question = getArguments().getInt("QUESTION");
        name = session.getData(Constant.QUESTION_NAME);
        tvTimer.setText(seconds + " Sec");
        tvQuestion.setText("Question "+question+" of 10");
        PractisesActivity.imgBack.setVisibility(View.GONE);
        PractisesActivity.imgHome.setVisibility(View.GONE);
        PractisesActivity.tilte.setText(Html.fromHtml( "Practises>"+Level+"><b>"+Title+"</b>"));
        quesProgress.setProgress(question);
        int noOfSeconds = Integer.parseInt(seconds) * 500;
        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + " Sec");
            }
            public void onFinish() {
                tvTimer.setText("Time out");
                showDialog();
            }
        }.start();
        setQuestion();


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

    private void setQuestion()
    {
        try {
            JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
            if (jsonObject.getBoolean(Constant.SUCCESS)){
                JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                JSONArray jsonArray1 = jsonArray.getJSONObject(question-1).getJSONArray(Constant.QUESTION);
                JSONArray jsonArray2 = jsonArray.getJSONObject(question-1).getJSONArray(Constant.ANSWERS);
                String question = jsonArray1.get(0).toString();
                actanswer = jsonArray2.get(0).toString();
                setImage(question);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setImage(String answer)
    {
        inVisibleAll();



        if (answer.length() == 1){
            first=answer.substring(0,1);
            column1(Integer.parseInt(first));

        }
        if (answer.length() == 2){
            first=answer.substring(0,1);
            second=answer.substring(1,2);
            column1(Integer.parseInt(first));
            column2(Integer.parseInt(second));

        }
        if (answer.length() == 3){
            first=answer.substring(0,1);
            second=answer.substring(1,2);
            third=answer.substring(2,3);
            column1(Integer.parseInt(first));
            column2(Integer.parseInt(second));
            column3(Integer.parseInt(third));

        }
        if (answer.length() == 4){
            first=answer.substring(0,1);
            second=answer.substring(1,2);
            third=answer.substring(2,3);
            fourth=answer.substring(3,4);
            column1(Integer.parseInt(first));
            column2(Integer.parseInt(second));
            column3(Integer.parseInt(third));
            column4(Integer.parseInt(fourth));

        }
        if (answer.length() == 5){
            first=answer.substring(0,1);
            second=answer.substring(1,2);
            third=answer.substring(2,3);
            fourth=answer.substring(3,4);
            fifth=answer.substring(4,5);
            column1(Integer.parseInt(first));
            column2(Integer.parseInt(second));
            column3(Integer.parseInt(third));
            column4(Integer.parseInt(fourth));
            column5(Integer.parseInt(fifth));

        }
    }

    private void column1(int i)
    {
        lcolumn1.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c1sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c1sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c1sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c1sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c1bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column2(int i)
    {
        lcolumn2.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c2sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c2sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c2sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c2sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c2bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column3(int i)
    {
        lcolumn3.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c3sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c3sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c3sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c3sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c3bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column4(int i)
    {
        lcolumn4.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c4sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c4sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c4sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c4sdice4.setVisibility(View.VISIBLE);

        }
        if ( i >= 5){
            c4bdice1.setVisibility(View.VISIBLE);
        }


    }
    private void column5(int i) {
        lcolumn5.setVisibility(View.VISIBLE);
        if (isBetween(i,5,10) || isBetween(i,0,5)){
            c5sdice1.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,6,10) || isBetween(i,1,5)){
            c5sdice2.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,7,10) || isBetween(i,2,5)){
            c5sdice3.setVisibility(View.VISIBLE);

        }
        if (isBetween(i,8,10) || isBetween(i,3,5)){
            c5sdice4.setVisibility(View.VISIBLE);
        }
        if ( i >= 5){
            c5bdice1.setVisibility(View.VISIBLE);
        }

    }
    public static boolean isBetween(int value, int min, int max)
    {
        return((value > min) && (value < max));
    }
    private void init(View root) {
        c1bdice1 = root.findViewById(R.id.c1bdice1);
        c1sdice1 = root.findViewById(R.id.c1sdice1);
        c1sdice2 = root.findViewById(R.id.c1sdice2);
        c1sdice3 = root.findViewById(R.id.c1sdice3);
        c1sdice4 = root.findViewById(R.id.c1sdice4);
        c2bdice1 = root.findViewById(R.id.c2bdice1);
        c2sdice1 = root.findViewById(R.id.c2sdice1);
        c2sdice2 = root.findViewById(R.id.c2sdice2);
        c2sdice3 = root.findViewById(R.id.c2sdice3);
        c2sdice4 = root.findViewById(R.id.c2sdice4);
        c3bdice1 = root.findViewById(R.id.c3bdice1);
        c3sdice1 = root.findViewById(R.id.c3sdice1);
        c3sdice2 = root.findViewById(R.id.c3sdice2);
        c3sdice3 = root.findViewById(R.id.c3sdice3);
        c3sdice4 = root.findViewById(R.id.c3sdice4);
        c4bdice1 = root.findViewById(R.id.c4bdice1);
        c4sdice1 = root.findViewById(R.id.c4sdice1);
        c4sdice2 = root.findViewById(R.id.c4sdice2);
        c4sdice3 = root.findViewById(R.id.c4sdice3);
        c4sdice4 = root.findViewById(R.id.c4sdice4);
        c5bdice1 = root.findViewById(R.id.c5bdice1);
        c5sdice1 = root.findViewById(R.id.c5sdice1);
        c5sdice2 = root.findViewById(R.id.c5sdice2);
        c5sdice3 = root.findViewById(R.id.c5sdice3);
        c5sdice4 = root.findViewById(R.id.c5sdice4);
        lcolumn1 = root.findViewById(R.id.lcolumn1);
        lcolumn2 = root.findViewById(R.id.lcolumn2);
        lcolumn3 = root.findViewById(R.id.lcolumn3);
        lcolumn4 = root.findViewById(R.id.lcolumn4);
        lcolumn5 = root.findViewById(R.id.lcolumn5);
        inVisibleAll();
    }
    private void inVisibleAll() {
        c1bdice1.setVisibility(View.GONE);
        c1sdice1.setVisibility(View.GONE);
        c1sdice2.setVisibility(View.GONE);
        c1sdice3.setVisibility(View.GONE);
        c1sdice4.setVisibility(View.GONE);
        c2bdice1.setVisibility(View.GONE);
        c2sdice1.setVisibility(View.GONE);
        c2sdice2.setVisibility(View.GONE);
        c2sdice3.setVisibility(View.GONE);
        c2sdice4.setVisibility(View.GONE);
        c3bdice1.setVisibility(View.GONE);
        c3sdice1.setVisibility(View.GONE);
        c3sdice2.setVisibility(View.GONE);
        c3sdice3.setVisibility(View.GONE);
        c3sdice4.setVisibility(View.GONE);
        c4bdice1.setVisibility(View.GONE);;
        c4sdice1.setVisibility(View.GONE);
        c4sdice2.setVisibility(View.GONE);
        c4sdice3.setVisibility(View.GONE);
        c4sdice4.setVisibility(View.GONE);
        c5bdice1.setVisibility(View.GONE);
        c5sdice1.setVisibility(View.GONE);
        c5sdice2.setVisibility(View.GONE);
        c5sdice3.setVisibility(View.GONE);
        c5sdice4.setVisibility(View.GONE);
        lcolumn1.setVisibility(View.GONE);
        lcolumn2.setVisibility(View.GONE);
        lcolumn3.setVisibility(View.GONE);
        lcolumn4.setVisibility(View.GONE);
        lcolumn5.setVisibility(View.GONE);
    }

    private void next()
    {
        ((PractisesActivity) requireActivity()).cancelTimer();
        if (question == 10){
            PractisesActivity.imgHome.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("SECONDS", seconds);
            ResultFragment resultFragment = new ResultFragment();
            resultFragment.setArguments(bundle);
            PractisesActivity.fm.beginTransaction().replace(R.id.container,  resultFragment,Constant.RESULTFRAGMENT).commit();

        }else {
            question = question + 1;
            nextQuestion();
        }
    }
    private void nextQuestion()
    {
        setQuestion();
        tvTimer.setText(seconds + " Sec");
        tvQuestion.setText("Question "+question+" of 10");
        quesProgress.setProgress(question);
        int noOfSeconds = Integer.parseInt(seconds) * 500;
        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + " Sec");
            }

            public void onFinish() {

                tvTimer.setText("Time out");
                showDialog();

            }
        }.start();
    }



    private void showDialog()
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.answer_custom_dialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnSubmit);
        EditText etAnswer = (EditText) dialog.findViewById(R.id.etAnswer);
        Log.e("check",name);
        if (name.equals("SD")) {
            etAnswer.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        }else if(name.equals("DD")){
            etAnswer.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        }
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAnswer.getText().toString().isEmpty()) {
                    ShowAlertDialog();
                }else {
                    if(actanswer.equals(etAnswer.getText().toString().trim())){
                        score = score + 1;
                        session.setData(Constant.SCORE,score +"");
                    }
                    if(!(etAnswer.getText().toString().isEmpty())) {
                        dialog.dismiss();
                        next();
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