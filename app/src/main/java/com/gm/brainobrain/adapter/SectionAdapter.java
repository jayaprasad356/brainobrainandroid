package com.gm.brainobrain.adapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gm.brainobrain.R;
import com.gm.brainobrain.activities.PractisesActivity;
import com.gm.brainobrain.fragments.AddSubNumTypeOralFragment;
import com.gm.brainobrain.fragments.AddSubNumTypeVisualFragment;
import com.gm.brainobrain.fragments.FlashCardsQuestionVisualFragment;
import com.gm.brainobrain.helper.ApiConfig;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.Section;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Section> sections;
    String type = "visual";
    Session session;

    public SectionAdapter(Activity activity, ArrayList<Section> sections) {
        this.activity = activity;
        this.sections = sections;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.section_lyt_item, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        session = new Session(activity);
        final ItemHolder holder = (ItemHolder) holderParent;
        final Section section = sections.get(position);

        holder.title.setText(section.getName());
        holder.subTitle.setText(section.getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbottomsheet(section.getId(),section.getType(),section.getName());
            }
        });
    }
    private void showbottomsheet(String id, String title, String name) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_lyt);
        TextView tvStart = bottomSheetDialog.findViewById(R.id.tvStart);
        TextView tvOralView = bottomSheetDialog.findViewById(R.id.tvOralView);
        TextView tvSec = bottomSheetDialog.findViewById(R.id.tvSec);
        TextView tvVisualview = bottomSheetDialog.findViewById(R.id.tvVisualview);
        Slider sliderTime = bottomSheetDialog.findViewById(R.id.sliderTime);
        LinearLayout lltimeinsec = bottomSheetDialog.findViewById(R.id.lltimeinsec);
        ImageView imgMan = bottomSheetDialog.findViewById(R.id.imgMan);
        ImageView imgWoman = bottomSheetDialog.findViewById(R.id.imgWoman);


        sliderTime.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = sliderTime.getValue();
                int i =  (int) value;
                tvSec.setText(  i+"");





            }
        });
        LinearLayout llOralvisible = bottomSheetDialog.findViewById(R.id.llOralvisible);
        if (title.equals("Flash Cards")){
            tvOralView.setVisibility(View.INVISIBLE);
        }
        tvStart.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            String sec = tvSec.getText().toString().trim();
            session.setData(Constant.SECONDS,sec);
            session.setData(Constant.TYPE,title);
            session.setData(Constant.SECTION_ID,id);
            session.setData(Constant.QUESTION_NAME, name);
            Bundle bundle = new Bundle();
            bundle.putInt("QUESTION", 1);
            getSections(bundle,title,type);


        });

        tvOralView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "oral";
                llOralvisible.setVisibility(View.VISIBLE);
                sliderTime.setVisibility(View.VISIBLE);
                lltimeinsec.setVisibility(View.VISIBLE);
                tvVisualview.setBackgroundResource(R.drawable.border13);
                tvOralView.setBackgroundResource(R.drawable.border5);


            }
        });
        tvVisualview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "visual";
                llOralvisible.setVisibility(View.GONE);
                sliderTime.setVisibility(View.GONE);
                lltimeinsec.setVisibility(View.GONE);
                tvOralView.setBackgroundResource(R.drawable.border13);
                tvVisualview.setBackgroundResource(R.drawable.border5);

            }
        });


        imgMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWoman.setBackgroundResource(R.drawable.border12);
                imgMan.setBackgroundResource(R.drawable.border14);
            }
        });
        imgWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMan.setBackgroundResource(R.drawable.border12);
                imgWoman.setBackgroundResource(R.drawable.border14);
            }
        });

        bottomSheetDialog.show();

    }

    private void getSections(Bundle bundle, String title, String type)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.TOKEN,session.getData(Constant.TOKEN));
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("SECTION_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        session.setData(Constant.QUESTION_ARRAY,response);
                        if (type.equals("oral")){
                            session.setData(Constant.QUES_TYPE,Constant.ORAL_NUMBER);
                            AddSubNumTypeOralFragment addSubNumTypeOralFragment = new AddSubNumTypeOralFragment();
                            addSubNumTypeOralFragment.setArguments(bundle);
                            PractisesActivity.fm.beginTransaction().replace(R.id.container, addSubNumTypeOralFragment,Constant.ADDSUBNUMTYPEORALFRAGMENT).commit();
                        }
                        else {
                            if (title.equals("Flash Cards")){
                                session.setData(Constant.QUES_TYPE,Constant.FLASH_CARD);
                                FlashCardsQuestionVisualFragment flashCardsQuestionVisualFragment = new FlashCardsQuestionVisualFragment();
                                flashCardsQuestionVisualFragment.setArguments(bundle);
                                PractisesActivity.fm.beginTransaction().replace(R.id.container, flashCardsQuestionVisualFragment, Constant.FLASHCARDQUESTIONFRAGMENT).commit();

                            }else {
                                session.setData(Constant.QUES_TYPE,Constant.NUMBER);
                                AddSubNumTypeVisualFragment addSubNumTypeVisualFragment = new AddSubNumTypeVisualFragment();
                                addSubNumTypeVisualFragment.setArguments(bundle);
                                PractisesActivity.fm.beginTransaction().replace(R.id.container, addSubNumTypeVisualFragment,Constant.ADDSUBNUMTYPEVISUALFRAGMENT).commit();

                            }

                        }


                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.PRACTICE_SECTION_QUESTION_URL(session.getData(Constant.LEVEL_ID),session.getData(Constant.SECTION_ID)), params, true,0);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView title,subTitle;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);


        }
    }
}


