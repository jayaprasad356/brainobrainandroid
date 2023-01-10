package com.gm.brainobrain.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gm.brainobrain.R;
import com.gm.brainobrain.model.StaticsData;

import java.util.ArrayList;

public class StaticsAdapter extends RecyclerView.Adapter<StaticsAdapter.ViewHolder> {
    Activity activity;
    ArrayList<StaticsData> staticsData;

    public StaticsAdapter(ArrayList<StaticsData> sakunaluDatas, Activity activity) {
        this.staticsData = sakunaluDatas;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.stats_lyt_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvAccuracy.setText(staticsData.get(position).getAccuracy());
        holder.tvActivity.setText(staticsData.get(position).getActivity());
        holder.tvLevel.setText(staticsData.get(position).getL_name());
        holder.tvSection.setText(staticsData.get(position).getS_name());
        holder.tvQuestions.setText(staticsData.get(position).getQuestions());
        holder.tvCorrectAns.setText(staticsData.get(position).getCorrect_answers());
        holder.tvTakenTime.setText(staticsData.get(position).getTime_taken());
        holder.tvAVT.setText(staticsData.get(position).getAverage_time_taken());
    }


    @Override
    public int getItemCount() {
        return staticsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvActivity, tvLevel, tvSection, tvQuestions, tvCorrectAns, tvTakenTime, tvAVT, tvAccuracy;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvActivity = (TextView) itemView.findViewById(R.id.tvActivity);
            this.tvLevel = (TextView) itemView.findViewById(R.id.tvLevelName);
            this.tvSection = (TextView) itemView.findViewById(R.id.tvSection);
            this.tvQuestions = (TextView) itemView.findViewById(R.id.tvQuestion);
            this.tvCorrectAns = (TextView) itemView.findViewById(R.id.tvCorrect);
            this.tvTakenTime = (TextView) itemView.findViewById(R.id.tvTimetaken);
            this.tvAVT = (TextView) itemView.findViewById(R.id.tvAvgTimetaken);
            this.tvAccuracy = (TextView) itemView.findViewById(R.id.tvAccuracy);
        }
    }
}
