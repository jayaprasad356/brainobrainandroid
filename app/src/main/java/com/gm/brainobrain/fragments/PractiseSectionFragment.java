package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gm.brainobrain.R;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.adapter.LevelAdapter;
import com.gm.brainobrain.adapter.SectionAdapter;
import com.gm.brainobrain.helper.ApiConfig;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.Level;
import com.gm.brainobrain.model.Section;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PractiseSectionFragment extends Fragment {
    RecyclerView recyclerView;
    Activity activity;
    SectionAdapter sectionAdapter;
    String Level, Id, doubleType;
    Session session;
    SwipeRefreshLayout swipe;

    public PractiseSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_practise_section, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        activity = getActivity();
        session = new Session(activity);
        Level = session.getData(Constant.LEVEL);
        Id = getArguments().getString(Constant.ID);
        doubleType = getArguments().getString("doubleType");
        swipe = root.findViewById(R.id.swipe);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
                Log.d("BACKBUTTON", "Back button clicks");
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        PractisesActivity.tilte.setText(Html.fromHtml("Practises><b>" + Level + "</b> "));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        session.setData(Constant.FRAG_LOCATE, Constant.SECTION_FRAG);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sectionList();

            }
        });
        sectionList();
        return root;
    }

    private void sectionList() {
        Log.d("TOKEN_PRACTISE", session.getData(Constant.TOKEN) + "\n" + Id);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.TOKEN, session.getData(Constant.TOKEN));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        swipe.setRefreshing(false);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Section> sections = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Section group = g.fromJson(jsonObject1.toString(), Section.class);
                                sections.add(group);
                            } else {
                                break;
                            }
                        }
                        if (doubleType !=null) {
                            if (doubleType.equals("1")) {
                                Section section = new Section();
                                section.setId("0");
                                section.setName("Doubling");
                                section.setType("Double the number");
                                sections.add(section);
                            }
                        }

                        sectionAdapter = new SectionAdapter(activity, sections);
                        recyclerView.setAdapter(sectionAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.PRACTICE_SECTION_URL(Id), params, true, 0);


    }
}