package com.gm.brainobrain.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gm.brainobrain.R;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.helper.Constant;

public class DoublingFragment extends Fragment {
    LinearLayout lldoubling;
    Activity activity;

    public DoublingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_doubling, container, false);

        activity = getActivity();
        lldoubling = root.findViewById(R.id.lldoubling);
        PractisesActivity.tilte.setText(Html.fromHtml( "Practises><b>Level 10</b> "));


        lldoubling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoublingViewFragment doublingViewFragment = new DoublingViewFragment();
                PractisesActivity.fm.beginTransaction().add(R.id.container, doublingViewFragment, Constant.DOUBLINGVIEWFRAGMENT).commit();
            }
        });


        return root;
    }
}