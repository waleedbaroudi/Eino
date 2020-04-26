package com.example.eino.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.R;
import com.example.eino.models.Categories;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {

    ArrayList<String> skills = Categories.getInstance().getAllsubcats();


    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_list_item, parent, false);
        SkillViewHolder skillViewHolder = new SkillViewHolder(view);
        return skillViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        holder.skillImage.setImageResource(R.drawable.c_icon_grad);
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
        return imageName.toLowerCase() + "_icon.png";
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {
        CircleImageView skillImage;
        TextView skillName;
        CheckBox skillCheck;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            skillImage = itemView.findViewById(R.id.skill_image);
            skillName = itemView.findViewById(R.id.skill_name);
            skillCheck = itemView.findViewById(R.id.skill_check);
        }
    }
}
