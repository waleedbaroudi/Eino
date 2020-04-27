package com.example.eino.models.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {

    ArrayList<String> skills;

    public SkillAdapter(ArrayList<String> skills) {
        this.skills = skills;
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
        int imageResId = holder.getImageResource(getImageName(skills.get(position)));
        holder.skillImage.setImageResource(imageResId);
        holder.skillName.setText(skills.get(position));
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public String getImageName(String name) {
        String imageName = name;
        if (name.toLowerCase().equals("c++"))
            imageName = "cpp";
        if (name.toLowerCase().equals("c#"))
            imageName = "csharp";
        return imageName.toLowerCase() + "_icon";
    }

    public void filteredList(ArrayList<String> filtered){
        skills = filtered;
        notifyDataSetChanged();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {
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
