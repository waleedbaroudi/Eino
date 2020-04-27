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
        holder.catName.setText(subcats.get(position));
        holder.item.setOnClickListener(v -> {
            Intent toUsersActivity = new Intent(v.getContext(), UsersActivity.class);
            toUsersActivity.putExtra("selectedSubCat", subcats.get(position));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.item_name);
            item = itemView.findViewById(R.id.item_layout);
        }
    }
}
