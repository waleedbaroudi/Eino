package com.example.eino.Models;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.Controllers.UserProfileActivity;
import com.example.eino.R;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>{
    String[] cats = {"Programming", "Cooking", "Repairing", "Studying", "Playing an Instrument", "Languages", "Others"};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.catName.setText(cats[position]);
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), UserProfileActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cats.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView catName;
        CardView itemCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.category_name);
            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
}
