package com.example.eino.models.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.R;
import com.example.eino.models.Skill;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {

    private static final String TAG = "SkillAdapter";

    Context context;
    private ArrayList<Skill> skills = new ArrayList<>();

    private ArrayList<String> selectedSkills = new ArrayList<>();

    public SkillAdapter(ArrayList<Skill> skills, Context context) {
        this.skills = skills;
        this.context = context;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_list_item, parent, false);
        SkillViewHolder skillViewHolder = new SkillViewHolder(view);
        return skillViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        final Skill skill = skills.get(position);
        int imageResId = holder.getImageResource(getImageName(skill.getName()));
        holder.skillCheck.setOnCheckedChangeListener(null);
        holder.skillCheck.setChecked(skill.isChecked());
        holder.skillCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                skill.setChecked(isChecked);
                if (isChecked) {
                    selectedSkills.add(skill.getName());
                    Log.d(TAG, "onCheckedChanged: added: " + skill.getName());
                } else {
                    Log.d(TAG, "onCheckedChanged: removed: " + skill.getName());
                    selectedSkills.remove(skill.getName());
                }
            }
        });

        holder.skillImage.setImageResource(imageResId);
        holder.skillName.setText(skill.getName());
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    private String getImageName(String str) {

        str = str.toLowerCase();
        String image = "";

        char[] imageChars = str.toCharArray();

        for(char c : imageChars) {
            if(c == ' ')
                continue;
            if(c == '+') {
                image += 'p';
                continue;
            }
            if(c == '#') {
                image += "sharp";
                continue;
            }
            image += c;
        }
        image += "_icon";
        return image;
    }

    public void filteredList(ArrayList<Skill> filtered) {
        skills = filtered;
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedSkills() {
        return selectedSkills;
    }

    public static class SkillViewHolder extends RecyclerView.ViewHolder {
        CircleImageView skillImage;
        TextView skillName;
        CheckBox skillCheck;

        View view;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            skillImage = itemView.findViewById(R.id.skill_image);
            skillName = itemView.findViewById(R.id.skill_name);
            skillCheck = itemView.findViewById(R.id.skill_check);
            view = itemView;
        }

        public int getImageResource(String imageName) {
            return view.getResources().getIdentifier(imageName, "drawable", view.getContext().getPackageName());
        }


    }
}
