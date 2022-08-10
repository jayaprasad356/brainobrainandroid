package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gm.brainobrain.R;
import com.gm.brainobrain.adapter.LevelAdapter;
import com.gm.brainobrain.helper.ApiConfig;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.Level;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PractisesLevelFragment extends Fragment {

    RecyclerView recyclerView;
    Activity activity;
    LevelAdapter levelAdapter;
    Session session;
    SwipeRefreshLayout swipe;



    public PractisesLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_practises_level, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        swipe = root.findViewById(R.id.swipe);
        activity = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        session = new Session(activity);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                levelList();

            }
        });
        levelList();


        return root;
    }
    private void levelList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.TOKEN,session.getData(Constant.TOKEN));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        swipe.setRefreshing(false);
                        JSONObject object = new JSONObject(response);
                        JSONObject jsonObject1 = object.getJSONObject(Constant.DATA);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray(Constant.LEVELS);
                        Gson g = new Gson();
                        ArrayList<Level> levels = new ArrayList<>();

                        for (int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                            if (jsonObject2 != null) {
                                Level group = g.fromJson(jsonObject2.toString(), Level.class);
                                levels.add(group);
                            } else {
                                break;
                            }
                        }
                        JSONArray jsonArray2 = jsonObject1.getJSONArray(Constant.USER_LEVELS);
                        ArrayList<Level> userlevels = new ArrayList<>();

                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                            if (jsonObject2 != null) {
                                Level group = g.fromJson(jsonObject2.toString(), Level.class);
                                userlevels.add(group);
                            } else {
                                break;
                            }
                        }
                        for (int i=0; i < levels.size(); i++){

                            for (int j=0; j < userlevels.size(); j++){
                                if(!(userlevels.get(j).getId().equals(levels.get(i).getId()))){

                                    //levels.get(i).setSelected(false);
                                    //do something for not equals
                                }else{
                                    levels.get(i).setSelected(true);
                                    //do something for equals
                                }
                            }
                        }
                        levelAdapter = new LevelAdapter(activity, levels,userlevels);
                        recyclerView.setAdapter(levelAdapter);

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
}