package com.app.brainobrain;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTitle(null);
        setCancelable(false);
        View view= LayoutInflater.from(context).inflate(R.layout.custom_dialog,null);
        setContentView(view);
    }

    public void startCountdown() {
       // final TextView countdownTextView = findViewById(R.id.countdown_textview);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int countdown = 10;

            @Override
            public void run() {
                //countdownTextView.setText("Please wait... " + countdown);
                countdown--;
                if (countdown >= 0) {
                    handler.postDelayed(this, 1000);
                } else {
                    dismiss();
                }


            }
        }, 0);
    }
}
