package com.app.brainobrain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.app.brainobrain.activities.DashboardActivity;
import com.app.brainobrain.helper.ApiConfig;
import com.app.brainobrain.helper.Constant;
import com.app.brainobrain.helper.Session;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin;

    Activity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        activity = LoginActivity.this;
        session = new Session(activity);
        btnLogin.setOnClickListener(view -> {
            if (etEmail.getText().toString().trim().equals("")){
                Snackbar.make(view, "Enter Email",Snackbar.LENGTH_SHORT).show();
            }
            else if (etPassword.getText().toString().trim().equals("")){
                Snackbar.make(view, "Enter Password",Snackbar.LENGTH_SHORT).show();
            }
            else if (!isValidEmail(etEmail.getText().toString().trim())){
                Snackbar.make(view, "Enter Valid Email",Snackbar.LENGTH_SHORT).show();

            }
            else{
                login();
            }

        });
    }


    private void login() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMAIL,etEmail.getText().toString().trim());
        params.put(Constant.PASSWORD,etPassword.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(Constant.DATA);
                        session.setBoolean("is_logged_in", true);
                        session.setData(Constant.TOKEN,jsonObject1.getString(Constant.TOKEN));
                        session.setData(Constant.NAME,jsonObject1.getString(Constant.NAME));
                        session.setData(Constant.EMAIL,etEmail.getText().toString().trim());
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, DashboardActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(activity, ""+jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }, activity, Constant.LOGIN_URL, params,true, 1);


    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}