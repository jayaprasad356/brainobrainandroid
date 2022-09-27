package com.gm.brainobrain.adapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.R;
import com.gm.brainobrain.fragments.DoublingFragment;
import com.gm.brainobrain.fragments.FlashCardFragment;
import com.gm.brainobrain.fragments.PractiseSectionFragment;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.Level;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;


public class LevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Level> levels;
    Session session;
    final ArrayList<Level> userLevels;


    public LevelAdapter(Activity activity, ArrayList<Level> levels, ArrayList<Level> userLevels) {
        this.activity = activity;
        this.levels = levels;
        this.userLevels = userLevels;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.level_lyt_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        session = new Session(activity);
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Level level = levels.get(position);
        holder.itemView.setEnabled(false);

        holder.tvLevelName.setText(level.getName());
        if (level.getName().equals("1")){
            holder.linearProgress.setProgress(0);
            holder.linearProgress.setMax(1);
        }
        if (level.isSelected()){
            holder.imgLock.setVisibility(View.INVISIBLE);
            holder.itemView.setEnabled(true);

        }
        holder.itemView.setOnClickListener(view -> {
            if (level.getName().equals("Level 10")){
                DoublingFragment doublingFragment = new DoublingFragment();
                PractisesActivity.fm.beginTransaction().replace(R.id.container, doublingFragment).commit();
            }
            else {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ID, level.getId());
                session.setData(Constant.LEVEL,level.getName());
                session.setData(Constant.LEVEL_ID,level.getId());
                PractiseSectionFragment practiseSectionFragment = new PractiseSectionFragment();
                practiseSectionFragment.setArguments(bundle);
                PractisesActivity.fm.beginTransaction().replace(R.id.container, practiseSectionFragment).commit();


            }

        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final TextView tvLevelName;
        LinearProgressIndicator linearProgress;
        ImageView imgLock;
        LinearLayout l1;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvLevelName = itemView.findViewById(R.id.tvLevelName);
            linearProgress = itemView.findViewById(R.id.linearProgress);
            imgLock = itemView.findViewById(R.id.imgLock);
            l1 = itemView.findViewById(R.id.l1);


        }
    }
}


