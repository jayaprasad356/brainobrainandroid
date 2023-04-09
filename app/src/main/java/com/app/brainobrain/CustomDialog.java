package com.app.brainobrain;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.custom_dialog);
        setCancelable(false);
    }

    public void startCountdown() {
        final TextView countdownTextView = findViewById(R.id.countdown_textview);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int countdown = 10;

            @Override
            public void run() {
                countdownTextView.setText("Please wait... " + countdown);
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
