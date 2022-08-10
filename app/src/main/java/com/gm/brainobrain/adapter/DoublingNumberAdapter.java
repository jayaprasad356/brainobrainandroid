package com.gm.brainobrain.adapter;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gm.brainobrain.R;
import com.gm.brainobrain.model.Number;

import java.util.ArrayList;


public class DoublingNumberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Number> numbers;

    public DoublingNumberAdapter(Activity activity, ArrayList<Number> numbers) {
        this.activity = activity;
        this.numbers = numbers;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.doubling_num_lyt_item, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Number number = numbers.get(position);
         if (getItemCount() == position + 1){
            holder.tvNumber.setTextColor(Color.WHITE);
            holder.rlNumber.setBackgroundResource(R.drawable.border15);
        }
        else {
            holder.tvNumber.setTextColor(Color.BLACK);
            holder.rlNumber.setBackgroundResource(R.drawable.border2);

        }

        holder.tvNumber.setText(number.getNumber());

    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView tvNumber;
        RelativeLayout rlNumber;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            rlNumber = itemView.findViewById(R.id.rlNumber);


        }
    }
}


