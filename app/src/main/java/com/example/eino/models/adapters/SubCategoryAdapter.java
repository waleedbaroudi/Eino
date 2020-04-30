package com.example.eino.models.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.R;
import com.example.eino.controllers.UsersActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private ArrayList<String> subcats;
    private String selectedCategory;

    public SubCategoryAdapter(ArrayList<String> subcats, String selectedCategory) {
        this.subcats = subcats;
        this.selectedCategory = selectedCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rounded_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String subcategory = subcats.get(position);
        int imageResId = holder.getImageResource(getImageName(subcats.get(position)));

        holder.image.setImageResource(imageResId);
        holder.catName.setText(subcategory);
        holder.item.setOnClickListener(v -> {
            Intent toUsersActivity = new Intent(v.getContext(), UsersActivity.class);
            toUsersActivity.putExtra("selectedSubCat", subcategory);
            toUsersActivity.putExtra("selectedCategory", selectedCategory);
            v.getContext().startActivity(toUsersActivity);
        });
    }

    @Override
    public int getItemCount() {
        return subcats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        ConstraintLayout item;
        CircleImageView image;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.item_name);
            item = itemView.findViewById(R.id.item_layout);
            image = itemView.findViewById(R.id.item_image);
            view = itemView;
        }
        public int getImageResource(String imageName) {
            return view.getResources().getIdentifier(imageName, "drawable", view.getContext().getPackageName());
        }
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
}
