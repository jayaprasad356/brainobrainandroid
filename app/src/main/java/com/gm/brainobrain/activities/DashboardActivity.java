package com.gm.brainobrain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gm.brainobrain.R;
import com.gm.brainobrain.adapter.LevelAdapter;
import com.gm.brainobrain.helper.ApiConfig;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.Level;
import com.google.android.material.progressindicator.CircularProgressIndicator;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import render.animations.Attention;
import render.animations.Bounce;
import render.animations.Flip;
import render.animations.Render;
import render.animations.Slide;

public class DashboardActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RelativeLayout rlPractice,rlTablePractice;
    ImageView imgMenu;
    CircularProgressIndicator cpbPractises,cpbTablePractises;
    TextView tvUserId,mylevelPractises,totallevelPractises,mylevelTablePractises,totallevelTablePractises,mylevelMultiTaskings,totallevelMultiTaskings,tvName,tvPractices;

    Session session;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        activity = DashboardActivity.this;
        session = new Session(activity);

        cpbPractises = findViewById(R.id.cpbPractises);
        cpbTablePractises = findViewById(R.id.cpbTablePractises);
        mylevelPractises = findViewById(R.id.mylevelPractises);
        totallevelPractises = findViewById(R.id.totallevelPractises);
        mylevelTablePractises = findViewById(R.id.mylevelTablePractises);
        totallevelTablePractises = findViewById(R.id.totallevelTablePractises);
        mylevelMultiTaskings = findViewById(R.id.mylevelMultiTaskings);
        totallevelMultiTaskings = findViewById(R.id.totallevelMultiTaskings);
        imgMenu = findViewById(R.id.imgMenu);
        rlPractice = findViewById(R.id.rlPractice);
        tvUserId = findViewById(R.id.tvUserId);
        tvName = findViewById(R.id.tvName);
        rlTablePractice = findViewById(R.id.rlTablePractice);
        tvPractices = findViewById(R.id.tvPractices);


        tvUserId.setText(session.getData(Constant.EMAIL));
        tvName.setText(session.getData(Constant.NAME));
        rlPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PractisesActivity.class);
                startActivity(intent);
                
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup(view);
            }
        });
        mylevelPractises.setText(((session.getData(Constant.USER_PRACTICE_LEVELS_COUNT).equals("")) ? "0" : session.getData(Constant.USER_PRACTICE_LEVELS_COUNT)));
        cpbPractises.setProgress(((session.getData(Constant.USER_PRACTICE_LEVELS_COUNT).equals("")) ? 0 : Integer.parseInt(session.getData(Constant.USER_PRACTICE_LEVELS_COUNT))));



    }

    @Override
    protected void onStart() {
        super.onStart();
        levelcount();

    }

    private void levelcount()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.TOKEN,session.getData(Constant.TOKEN));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONObject jsonObject1 = object.getJSONObject(Constant.DATA);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray(Constant.LEVELS);
                        JSONArray jsonArray2 = jsonObject1.getJSONArray(Constant.USER_LEVELS);
                        session.setData(Constant.USER_PRACTICE_LEVELS_COUNT,jsonArray2.length() + "");
                        cpbPractises.setProgress(Integer.parseInt(session.getData(Constant.USER_PRACTICE_LEVELS_COUNT)));
                        mylevelPractises.setText(session.getData(Constant.USER_PRACTICE_LEVELS_COUNT));



                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.PRACTICE_LEVEL_URL, params, true,0);
    }

    public void showpopup(View v){
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.logoutitem){
            session.logoutUser(activity);
        }
        return false;
    }
}