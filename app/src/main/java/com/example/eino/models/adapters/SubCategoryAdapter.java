package com.example.eino.models.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.R;
import com.example.eino.controllers.UserProfileActivity;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private ArrayList<String> subcats;

    public SubCategoryAdapter(ArrayList<String> subcats) {
        this.subcats = subcats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.catName.setText(subcats.get(position));
        holder.itemCard.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), UserProfileActivity.class)));
    }

    @Override
    public int getItemCount() {
        return subcats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        CardView itemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.category_name);
            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
}
