package com.app.brainobrain.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.brainobrain.CustomDialog;
import com.app.brainobrain.LoginActivity;
import com.app.brainobrain.activities.DashboardActivity;
import com.developer.kalert.KAlertDialog;
import com.app.brainobrain.R;
import com.app.brainobrain.activities.PractisesActivity;
import com.app.brainobrain.adapter.QuestionAdapter;
import com.app.brainobrain.helper.Constant;
import com.app.brainobrain.helper.Session;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;


public class AddSubNumTypeVisualFragment extends Fragment {
    RecyclerView recyclerView;
    Activity activity;
    TextView tvTimer;
    String Seconds, digitsString;
    QuestionAdapter questionAdapter;
    String seconds;
    int minimum;
    int maximum;
    int digits;
    int answer;
    int question;
    LinearProgressIndicator quesProgress;
    TextView tvQuestion;
    private boolean running;
    private int s = 0;
    Session session;
    RelativeLayout rl;
    String Level, Title, Number;
    EditText etAnswer;
    Button btnSubmit;
    TextView tvQuestionContent;
    Boolean nextquestion = false;
    int score = 0;
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
        View root = inflater.inflate(R.layout.fragment_add_sub_num_type_visual, container, false);
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
        session.setData(Constant.SCORE, "0");
        session.setData(Constant.FRAG_LOCATE, Constant.EVENT_FRAG);
        tvQuestion.setText("Question " + question + " of 10");
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
        minimum = Integer.parseInt(session.getData(Constant.MINIMUM));
        maximum = Integer.parseInt(session.getData(Constant.MAXIMUM));
        digitsString = session.getData(Constant.DIGITS);
        if (!digitsString.isEmpty()) {
            digits = Integer.parseInt(session.getData(Constant.DIGITS));
            InputFilter[] inputFilters = new InputFilter[]{new InputFilter.LengthFilter(digits)};
            etAnswer.setFilters(inputFilters);

        }

//        etAnswer.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                // Do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                // Do nothing
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String userInput = editable.toString();
//                if (!userInput.isEmpty() && !userInput.equals(".")) {
//                    int number = Integer.parseInt(userInput);
//
//
//                    if (digitsString.isEmpty()) {
//                        if (number >= minimum && number <= maximum) {
//                            etAnswer.setError(null); // Clear any previous error
//                        } else {
//                            if (digitsString.isEmpty())
//                                Toast.makeText(activity, "Please enter a number between" + minimum + " to " + maximum + "", Toast.LENGTH_SHORT).show();
//                            etAnswer.setText("");
//                        }
//                    } else {
//                        if (number >= minimum && number <= maximum && userInput.length() <= digits) {
//                            etAnswer.setError(null);
//                        } else {
//                                Toast.makeText(activity, "Please enter a " + digits + " digits number between" + minimum + " to " + maximum + "", Toast.LENGTH_SHORT).show();
//                            etAnswer.setText("");
//                        }
//                    }
//                }
//            }
//        });

        requireActivity().

                getOnBackPressedDispatcher().

                addCallback(getViewLifecycleOwner(), callback);
        PractisesActivity.imgHome.setVisibility(View.INVISIBLE);
        PractisesActivity.tilte.setText(Html.fromHtml("Practises>" + Level + "><b>" + Title + "</b>"));
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


    private void nextQuestion() {
        ((PractisesActivity) requireActivity()).cancelTimer();
        numberList();
        tvQuestion.setText("Question " + question + " of 10");
        quesProgress.setProgress(question);
        int noOfSeconds = Integer.parseInt(seconds) * 1000;
        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + "");
                cpbTime.setProgress(Integer.parseInt("" + millisUntilFinished / 1000));
            }

            public void onFinish() {
                //tvTimer.setText("Time out");
                next();
            }
        }.start();
    }

    private void next() {
        String answerString = etAnswer.getText().toString().trim();
        if (answerString.isEmpty()) {
            ShowAlertDialog();
            return;
        }
        try {
            answer = Integer.parseInt(answerString);
        } catch (NumberFormatException e) {
            ShowAlertDialog();
            return;
        }
        if (answerString.equals(".")) {
            ShowAlertDialog();
            return;
        }
        if (!(answer >= minimum) || !(answer <= maximum) || !(etAnswer.getText().length() <= digits)) {
            Toast.makeText(activity, "Please enter a " + digits + " digits number between" + minimum + " to " + maximum + "", Toast.LENGTH_SHORT).show();
            return;

        }
        if (actanswer.equals(answerString)) {
            score++;
            session.setData(Constant.SCORE, score + "");
        }
        if (question == 10) {
            if (isAdded()) {
                ((PractisesActivity) requireActivity()).resetTimer();
                PractisesActivity.imgHome.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putString("SECONDS", seconds);
                ResultFragment resultFragment = new ResultFragment();
                resultFragment.setArguments(bundle);
                PractisesActivity.fm.beginTransaction().replace(R.id.container, resultFragment, Constant.RESULTFRAGMENT).commit();
            }
        } else {
            question++;
            etAnswer.getText().clear();
            nextQuestion();
        }
    }


    private void ShowAlertDialog() {
        if (getActivity() != null) {
            new KAlertDialog(requireActivity(), KAlertDialog.WARNING_TYPE)
                    .setTitleText("oops")
                    .setContentText("Enter your answer")
                    .setConfirmText("ok")
                    .show();
        }
    }


    private String numberList() {

        String Question = "";
        try {
            JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
            Log.d("ADDSUB_RES", session.getData(Constant.QUESTION_ARRAY));
            if (jsonObject.getBoolean(Constant.SUCCESS)) {
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
        PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + "");
                cpbTime.setProgress(Integer.parseInt("" + millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                //tvTimer.setText("Time out");
                next();
            }
        }.start();
    }

    public static class AddSubNumTypeOralFragment extends Fragment {
        Activity activity;
        TextView tvTimer;
        String seconds;
        int question;
        LinearProgressIndicator quesProgress;
        TextView tvQuestion, tvNumber;
        Session session;
        RelativeLayout rl;
        String Level, Title, Number, digitsString;
        private int s = 0;
        int noOfSeconds;
        TextToSpeech tts;
        boolean ttsspeak = false;
        int qno = 0, qpno = 0;
        boolean nextquestion = false;
        int score = 0;
        String actanswer;
        int countcomp = 0;
        CircularProgressIndicator cpbTime;
        Handler handler;

        int minimum;
        int maximum;
        int digits;
        int answer;

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
            session.setData(Constant.FRAG_LOCATE, Constant.EVENT_FRAG);
            session.setData(Constant.SCORE, "0");
            question = getArguments().getInt("QUESTION");

            double d = Double.parseDouble(seconds);

            cpbTime.setMax((int) d);
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                @Override
                public void handleOnBackPressed() {
                    // Handle the back button even
                    Log.d("BACKBUTTON", "Back button clicks");
                }
            };
            minimum = Integer.parseInt(session.getData(Constant.MINIMUM));
            maximum = Integer.parseInt(session.getData(Constant.MAXIMUM));
            digitsString = session.getData(Constant.DIGITS);

            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

            Set<String> a = new HashSet<>();
            a.add("male");
            Voice v = new Voice("hi-in-x-hie-local", new Locale("hi_IN"), 400, 200, false, a);
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
            tvQuestion.setText("Question " + question + " of 10");
            PractisesActivity.imgHome.setVisibility(View.INVISIBLE);

            PractisesActivity.tilte.setText(Html.fromHtml("Practises>" + Level + "><b>" + Title + "</b>"));

            quesProgress.setProgress(question);

            noOfSeconds = (int) (d * 1000);

            handler = new Handler();
            GotoActivity();

            getQuestion();


            CustomDialog customDialog = new CustomDialog(getActivity());
            customDialog.show();
            customDialog.startCountdown();


            return root;
        }


        private void GotoActivity() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    countdownTime();

                    ((PractisesActivity) requireActivity()).startTimer();


                }
            }, 10000);

        }


        private void speakNumber(String number) {
            boolean completed = false;

            new CountDownTimer(noOfSeconds, 2000) {
                @Override
                public void onTick(long l) {
                    countcomp = countcomp + 1;
                    Log.d("SPEAKCOUNTD", countcomp + "");
                    if (countcomp < 3) {
                        tts.speak(number, TextToSpeech.QUEUE_FLUSH, null); //speak after 1000ms

                    }

                }

                @Override
                public void onFinish() {

                }
            }.start();

        }

        private void countdownTime() {
            Number = getQuestion();
            tvNumber.setText(Number);
            Random r = new Random();
            int CustomColors = Color.argb(225, r.nextInt(256), r.nextInt(256), r.nextInt(256));
            tvNumber.setTextColor(CustomColors);
            if (ttsspeak == true) {
                tts.speak(Number.replaceAll("-", "less ").replaceAll("x", "multiplied by").replaceAll("/", "divided by"), TextToSpeech.QUEUE_FLUSH, null);
            } else {
                speakNumber(Number.replaceAll("-", "less ").replaceAll("x", "multiplied by").replaceAll("/", "divided by"));
            }


            tvQuestion.setText("Question " + question + " of 10");
            quesProgress.setProgress(question);

            if (seconds.equals("0.5")) {
                tvTimer.setText("0.5");
                PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 500) {

                    public void onTick(long millisUntilFinished) {
                        tvTimer.setText(millisUntilFinished / 500 + "");
                        cpbTime.setProgress(Integer.parseInt("" + millisUntilFinished / 500));
                    }

                    public void onFinish() {
                        ttsspeak = true;
                        if (nextquestion) {
                            showDialog();
                        } else {
                            countdownTime();
                        }

                    }
                }.start();
            } else if(seconds.equals("1.0")){
                tvTimer.setText("1.0");
                PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvTimer.setText(millisUntilFinished / 1000 + "");
                        cpbTime.setProgress(Integer.parseInt("" + millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        ttsspeak = true;
                        if (nextquestion) {
                            showDialog();
                        } else {
                            countdownTime();
                        }

                    }
                }.start();
            }else  {
                PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvTimer.setText(millisUntilFinished / 1000 + "");
                        cpbTime.setProgress(Integer.parseInt("" + millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        ttsspeak = true;
                        if (nextquestion) {
                            showDialog();
                        } else {
                            countdownTime();
                        }

                    }
                }.start();
            }
        }


        private String getQuestion() {
            Log.d("ORALTYPE", session.getData(Constant.QUESTION_ARRAY));
            String Question = "";
            try {
                JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
                if (jsonObject.getBoolean(Constant.SUCCESS)) {

                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                    JSONArray jsonArray1 = jsonArray.getJSONObject(question - 1).getJSONArray(Constant.QUESTION);
                    JSONArray jsonArray2 = jsonArray.getJSONObject(question - 1).getJSONArray(Constant.ANSWERS);
                    actanswer = jsonArray2.get(0).toString();
                    if (session.getData(Constant.TYPE).equals("Multiplication") || session.getData(Constant.TYPE).equals("Division")) {
                        ArrayList<String> questions = new ArrayList<>();

                        for (int i = 0; i < jsonArray1.length(); i++) {
                            questions.add(jsonArray1.get(i).toString());
                        }
                        String symbol = "";
                        if (session.getData(Constant.TYPE).equals("Multiplication")) {
                            symbol = " x ";
                        } else if (session.getData(Constant.TYPE).equals("Division")) {
                            symbol = " / ";

                        }
                        String s = "";
                        for (int i = 0; i < questions.size(); i++) {
                            if (i != questions.size() - 1) {
                                s += questions.get(i) + symbol;
                            } else {
                                s += questions.get(i) + "";

                            }

                        }
                        Question = s;
                        nextquestion = true;
                        qpno = 0;


                    } else {

                        Question = jsonArray1.get(qpno).toString();
                        if (jsonArray1.length() - 1 == qpno) {
                            nextquestion = true;
                            qpno = 0;

                        } else {
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


        private void showDialog() {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.answer_custom_dialog);

            EditText etAnswer = (EditText) dialog.findViewById(R.id.etAnswer);
            Button dialogButton = (Button) dialog.findViewById(R.id.btnSubmit);
            if (!digitsString.isEmpty()) {
                digits = Integer.parseInt(session.getData(Constant.DIGITS));
                InputFilter[] inputFilters = new InputFilter[]{new InputFilter.LengthFilter(digits)};
                etAnswer.setFilters(inputFilters);
            }
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String answerString = etAnswer.getText().toString();
                    if (!answerString.isEmpty()) {
                        answer = Integer.parseInt(answerString);
                    }
                    if (etAnswer.getText().toString().trim().isEmpty()) {
                        ShowAlertDialog();
                    } else if (etAnswer.getText().toString().trim().equals(".")) {
                        ShowAlertDialog();
                    } else if (!(answer >= minimum) || !(answer <= maximum)) {
                        Toast.makeText(activity, "Please enter a " + digits + " digits number between" + minimum + " to " + maximum + "", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        if (!etAnswer.getText().toString().trim().equals("")) {
                            if (actanswer.equals(etAnswer.getText().toString().trim())) {
                                score = score + 1;
                                session.setData(Constant.SCORE, score + "");
                            }

                        }
                        if (question == 10) {
                            ((PractisesActivity) requireActivity()).resetTimer();
                            PractisesActivity.imgHome.setVisibility(View.VISIBLE);
                            Bundle bundle = new Bundle();
                            bundle.putString("SECONDS", seconds);
                            ResultFragment resultFragment = new ResultFragment();
                            resultFragment.setArguments(bundle);
                            PractisesActivity.fm.beginTransaction().replace(R.id.container, resultFragment, Constant.RESULTFRAGMENT).commit();

                        } else {
                            question = question + 1;
                            countdownTime();

                        }


                    }


                }
            });

            dialog.show();
        }

        private void ShowAlertDialog() {
            new KAlertDialog(requireActivity(), KAlertDialog.WARNING_TYPE)
                    .setTitleText("oops")
                    .setContentText("Enter your answer")
                    .setConfirmText("ok")
                    .show();
        }
    }

    public static class FlashCardsQuestionVisualFragment extends Fragment {
        private int s = 0;
        private boolean running;
        TextView tvTimer;
        Activity activity;
        String seconds, digitsString;
        int question;
        LinearProgressIndicator quesProgress;
        TextView tvQuestion;
        Session session;
        EditText etAnswer;
        ImageView c1bdice1, c1sdice1, c1sdice2, c1sdice3, c1sdice4;
        ImageView c2bdice1, c2sdice1, c2sdice2, c2sdice3, c2sdice4;
        ImageView c3bdice1, c3sdice1, c3sdice2, c3sdice3, c3sdice4;
        ImageView c4bdice1, c4sdice1, c4sdice2, c4sdice3, c4sdice4;
        ImageView c5bdice1, c5sdice1, c5sdice2, c5sdice3, c5sdice4;
        LinearLayout lcolumn1, lcolumn2, lcolumn3, lcolumn4, lcolumn5;
        String first, second, third, fourth, fifth;
        String Level, Title;
        int score = 0;
        String actanswer, name;
        CircularProgressIndicator cpbTime;
        int minimum, maximum, digits;
        int answer;
        int noOfSeconds;

        public FlashCardsQuestionVisualFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View root = inflater.inflate(R.layout.fragment_flash_cards_question_visual, container, false);
            activity = getActivity();
            session = new Session(activity);

            tvTimer = root.findViewById(R.id.tvTimer);
            quesProgress = root.findViewById(R.id.quesProgress);
            tvQuestion = root.findViewById(R.id.tvQuestion);
            cpbTime = root.findViewById(R.id.cpbTime);
            init(root);
            session.setData(Constant.FRAG_LOCATE, Constant.EVENT_FRAG);
            Level = session.getData(Constant.LEVEL);
            Title = session.getData(Constant.TYPE);
            seconds = session.getData(Constant.SECONDS);
            session.setData(Constant.SCORE, "0");
            question = getArguments().getInt("QUESTION");
            name = session.getData(Constant.QUESTION_NAME);
            tvTimer.setText(seconds);
            tvQuestion.setText("Question " + question + " of 10");
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                @Override
                public void handleOnBackPressed() {
                    // Handle the back button even
                    Log.d("BACKBUTTON", "Back button clicks");
                }
            };

            try {
                minimum = Integer.parseInt(session.getData(Constant.MINIMUM));
            } catch (NumberFormatException e) {
                // handle the error here, e.g. show an error message or use a default value
            }

            try {
                maximum = Integer.parseInt(session.getData(Constant.MAXIMUM));
            } catch (NumberFormatException e) {
                // handle the error here, e.g. show an error message or use a default value
            }

            try {
                noOfSeconds = Integer.parseInt(seconds) * 500;
            } catch (NumberFormatException e) {
                // handle the error here, e.g. show an error message or use a default value
            }

            digitsString = session.getData(Constant.DIGITS);

            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

            PractisesActivity.imgHome.setVisibility(View.GONE);
            PractisesActivity.tvTimer.setVisibility(View.VISIBLE);
            ((PractisesActivity) requireActivity()).startTimer();
            PractisesActivity.tilte.setText(Html.fromHtml("Practises>" + Level + "><b>" + Title + "</b>"));
            quesProgress.setProgress(question);
            PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {
                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(millisUntilFinished / 1000 + "");
                    cpbTime.setProgress(Integer.parseInt("" + millisUntilFinished / 1000));
                }

                public void onFinish() {
                    //tvTimer.setText("Time out");
                    showDialog();
                }
            }.start();
            setQuestion();


            return root;
        }


        private void setQuestion() {
            try {
                JSONObject jsonObject = new JSONObject(session.getData(Constant.QUESTION_ARRAY));
                if (jsonObject.getBoolean(Constant.SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                    JSONArray jsonArray1 = jsonArray.getJSONObject(question - 1).getJSONArray(Constant.QUESTION);
                    JSONArray jsonArray2 = jsonArray.getJSONObject(question - 1).getJSONArray(Constant.ANSWERS);
                    String question = jsonArray1.get(0).toString();
                    actanswer = jsonArray2.get(0).toString();
                    setImage(question);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        private void setImage(String answer) {
            inVisibleAll();


            if (answer.length() == 1) {
                first = answer.substring(0, 1);
                column1(Integer.parseInt(first));

            }
            if (answer.length() == 2) {
                first = answer.substring(0, 1);
                second = answer.substring(1, 2);
                column1(Integer.parseInt(first));
                column2(Integer.parseInt(second));

            }
            if (answer.length() == 3) {
                first = answer.substring(0, 1);
                second = answer.substring(1, 2);
                third = answer.substring(2, 3);
                column1(Integer.parseInt(first));
                column2(Integer.parseInt(second));
                column3(Integer.parseInt(third));

            }
            if (answer.length() == 4) {
                first = answer.substring(0, 1);
                second = answer.substring(1, 2);
                third = answer.substring(2, 3);
                fourth = answer.substring(3, 4);
                column1(Integer.parseInt(first));
                column2(Integer.parseInt(second));
                column3(Integer.parseInt(third));
                column4(Integer.parseInt(fourth));

            }
            if (answer.length() == 5) {
                first = answer.substring(0, 1);
                second = answer.substring(1, 2);
                third = answer.substring(2, 3);
                fourth = answer.substring(3, 4);
                fifth = answer.substring(4, 5);
                column1(Integer.parseInt(first));
                column2(Integer.parseInt(second));
                column3(Integer.parseInt(third));
                column4(Integer.parseInt(fourth));
                column5(Integer.parseInt(fifth));

            }
        }

        private void column1(int i) {
            lcolumn1.setVisibility(View.VISIBLE);
            if (isBetween(i, 5, 10) || isBetween(i, 0, 5)) {
                c1sdice1.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 6, 10) || isBetween(i, 1, 5)) {
                c1sdice2.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 7, 10) || isBetween(i, 2, 5)) {
                c1sdice3.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 8, 10) || isBetween(i, 3, 5)) {
                c1sdice4.setVisibility(View.VISIBLE);

            }
            if (i >= 5) {
                c1bdice1.setVisibility(View.VISIBLE);
            }


        }

        private void column2(int i) {
            lcolumn2.setVisibility(View.VISIBLE);
            if (isBetween(i, 5, 10) || isBetween(i, 0, 5)) {
                c2sdice1.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 6, 10) || isBetween(i, 1, 5)) {
                c2sdice2.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 7, 10) || isBetween(i, 2, 5)) {
                c2sdice3.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 8, 10) || isBetween(i, 3, 5)) {
                c2sdice4.setVisibility(View.VISIBLE);

            }
            if (i >= 5) {
                c2bdice1.setVisibility(View.VISIBLE);
            }


        }

        private void column3(int i) {
            lcolumn3.setVisibility(View.VISIBLE);
            if (isBetween(i, 5, 10) || isBetween(i, 0, 5)) {
                c3sdice1.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 6, 10) || isBetween(i, 1, 5)) {
                c3sdice2.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 7, 10) || isBetween(i, 2, 5)) {
                c3sdice3.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 8, 10) || isBetween(i, 3, 5)) {
                c3sdice4.setVisibility(View.VISIBLE);

            }
            if (i >= 5) {
                c3bdice1.setVisibility(View.VISIBLE);
            }


        }

        private void column4(int i) {
            lcolumn4.setVisibility(View.VISIBLE);
            if (isBetween(i, 5, 10) || isBetween(i, 0, 5)) {
                c4sdice1.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 6, 10) || isBetween(i, 1, 5)) {
                c4sdice2.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 7, 10) || isBetween(i, 2, 5)) {
                c4sdice3.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 8, 10) || isBetween(i, 3, 5)) {
                c4sdice4.setVisibility(View.VISIBLE);

            }
            if (i >= 5) {
                c4bdice1.setVisibility(View.VISIBLE);
            }


        }

        private void column5(int i) {
            lcolumn5.setVisibility(View.VISIBLE);
            if (isBetween(i, 5, 10) || isBetween(i, 0, 5)) {
                c5sdice1.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 6, 10) || isBetween(i, 1, 5)) {
                c5sdice2.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 7, 10) || isBetween(i, 2, 5)) {
                c5sdice3.setVisibility(View.VISIBLE);

            }
            if (isBetween(i, 8, 10) || isBetween(i, 3, 5)) {
                c5sdice4.setVisibility(View.VISIBLE);
            }
            if (i >= 5) {
                c5bdice1.setVisibility(View.VISIBLE);
            }

        }

        public static boolean isBetween(int value, int min, int max) {
            return ((value > min) && (value < max));
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
            c1bdice1.setVisibility(View.INVISIBLE);
            c1sdice1.setVisibility(View.GONE);
            c1sdice2.setVisibility(View.GONE);
            c1sdice3.setVisibility(View.GONE);
            c1sdice4.setVisibility(View.GONE);
            c2bdice1.setVisibility(View.INVISIBLE);
            c2sdice1.setVisibility(View.GONE);
            c2sdice2.setVisibility(View.GONE);
            c2sdice3.setVisibility(View.GONE);
            c2sdice4.setVisibility(View.GONE);
            c3bdice1.setVisibility(View.INVISIBLE);
            c3sdice1.setVisibility(View.GONE);
            c3sdice2.setVisibility(View.GONE);
            c3sdice3.setVisibility(View.GONE);
            c3sdice4.setVisibility(View.GONE);
            c4bdice1.setVisibility(View.INVISIBLE);
            ;
            c4sdice1.setVisibility(View.GONE);
            c4sdice2.setVisibility(View.GONE);
            c4sdice3.setVisibility(View.GONE);
            c4sdice4.setVisibility(View.GONE);
            c5bdice1.setVisibility(View.INVISIBLE);
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

        private void next() {

            ((PractisesActivity) requireActivity()).cancelTimer();
            if (question == 10) {
                ((PractisesActivity) requireActivity()).resetTimer();
                PractisesActivity.imgHome.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putString("SECONDS", seconds);
                ResultFragment resultFragment = new ResultFragment();
                resultFragment.setArguments(bundle);
                PractisesActivity.fm.beginTransaction().replace(R.id.container, resultFragment, Constant.RESULTFRAGMENT).commit();

            } else {
                question = question + 1;
                nextQuestion();
            }
        }

        private void nextQuestion() {
            setQuestion();
            tvTimer.setText(seconds);
            tvQuestion.setText("Question " + question + " of 10");
            quesProgress.setProgress(question);
            int noOfSeconds = Integer.parseInt(seconds) * 500;
            PractisesActivity.cTimer = new CountDownTimer(noOfSeconds, 1000) {

                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(millisUntilFinished / 1000 + "");
                    cpbTime.setProgress(Integer.parseInt("" + millisUntilFinished / 1000));
                }

                public void onFinish() {

                    //tvTimer.setText("Time out");
                    showDialog();

                }
            }.start();
        }


        private void showDialog() {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.answer_custom_dialog);
            Button dialogButton = (Button) dialog.findViewById(R.id.btnSubmit);
            etAnswer = (EditText) dialog.findViewById(R.id.etAnswer);
            if (!digitsString.isEmpty()) {
                digits = Integer.parseInt(session.getData(Constant.DIGITS));
                InputFilter[] inputFilters = new InputFilter[]{new InputFilter.LengthFilter(digits)};
                etAnswer.setFilters(inputFilters);
            }
            //       etAnswer.addTextChangedListener(new TextWatcher() {
            //            @Override
            //            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //                // Do nothing
            //            }
            //
            //            @Override
            //            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //                // Do nothing
            //            }
            //
            //            @Override
            //            public void afterTextChanged(Editable editable) {
            //                String userInput = editable.toString();
            //                if (!userInput.isEmpty() && !userInput.equals(".")) {
            //                    int number = Integer.parseInt(userInput);
            //
            //
            //                    if (digitsString.isEmpty()) {
            //                        if (number >= minimum && number <= maximum) {
            //                            etAnswer.setError(null); // Clear any previous error
            //                        } else {
            //                            if (digitsString.isEmpty())
            //                                Toast.makeText(activity, "Please enter a number between" + minimum + " to " + maximum + "", Toast.LENGTH_SHORT).show();
            //                            etAnswer.setText("");
            //                        }
            //                    } else {
            //                        if (number >= minimum && number <= maximum && userInput.length() <= digits) {
            //                            etAnswer.setError(null);
            //                        } else {
            //                            Toast.makeText(activity, "Please enter a " + digits + " digits number between" + minimum + " to " + maximum + "", Toast.LENGTH_SHORT).show();
            //                            etAnswer.setText("");
            //                        }
            //                    }
            //                }
            //            }
            //        });


            Log.e("check", name);
            if (name.equals("SD")) {
                etAnswer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            } else if (name.equals("DD")) {
                etAnswer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
            }
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String answerString = etAnswer.getText().toString();
                    if (!answerString.isEmpty()) {
                        answer = Integer.parseInt(answerString);
                    }
                    if (etAnswer.getText().toString().trim().isEmpty()) {
                        ShowAlertDialog();
                    } else if (etAnswer.getText().toString().trim().equals(".")) {
                        ShowAlertDialog();
                    } else if (!(answer >= minimum) || !(answer <= maximum)) {
                        Toast.makeText(activity, "Please enter a " + digits + " digits number between" + minimum + " to " + maximum + "", Toast.LENGTH_SHORT).show();
                    } else {
                        if (actanswer.equals(etAnswer.getText().toString().trim())) {
                            score = score + 1;
                            session.setData(Constant.SCORE, score + "");
                        }
                        if (!(etAnswer.getText().toString().isEmpty())) {
                            dialog.dismiss();
                            next();
                        }
                    }
                }
            });
            dialog.show();
        }

        private void ShowAlertDialog() {
            new KAlertDialog(requireActivity(), KAlertDialog.WARNING_TYPE)
                    .setTitleText("oops")
                    .setContentText("Enter your answer")
                    .setConfirmText("ok")
                    .show();
        }

    }
}