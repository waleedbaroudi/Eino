package com.example.eino.models.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.controllers.SubCategoryActivity;
import com.example.eino.R;
import com.example.eino.models.Category;

import java.util.ArrayList;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {
    ArrayList<Category> cats;

    public MainCategoryAdapter(ArrayList<Category> cats) {
        this.cats = cats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.catName.setText(cats.get(position).getType());
        holder.itemCard.setOnClickListener(v -> {
            Intent toSubCats = new Intent(v.getContext(), SubCategoryActivity.class);
            toSubCats.putExtra("subcategories", cats.get(position).getSubCategories());
            toSubCats.putExtra("selectedCategory", cats.get(position).getType());
            v.getContext().startActivity(toSubCats);
        });
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        CardView itemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.item_name);
            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
}
