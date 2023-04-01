package com.app.brainobrain.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.brainobrain.R;

import java.util.ArrayList;


public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<String> questions;

    public QuestionAdapter(Activity activity, ArrayList<String> questions) {
        this.activity = activity;
        this.questions = questions;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.number_lyt_item, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final String question = questions.get(position).trim();

        holder.tvNumber.setText(question);

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView tvNumber;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);


        }
    }
}


