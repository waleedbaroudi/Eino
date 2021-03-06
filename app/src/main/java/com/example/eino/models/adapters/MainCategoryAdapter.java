package com.example.eino.models.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.controllers.SubCategoryActivity;
import com.example.eino.R;
import com.example.eino.models.Category;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {
    ArrayList<Category> cats;

    public MainCategoryAdapter(ArrayList<Category> cats) {
        this.cats = cats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rounded_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Category current = cats.get(position);

        int imageResId = holder.getImageResource(getImageName(current.getType()));

        holder.image.setImageResource(imageResId);
        holder.catDescription.setText(current.getDescription());
        holder.catName.setText(current.getType());
        holder.item.setOnClickListener(v -> {
            Intent toSubCats = new Intent(v.getContext(), SubCategoryActivity.class);
            toSubCats.putExtra("subcategories", current.getSubCategories());
            toSubCats.putExtra("selectedCategory", current.getType());
            v.getContext().startActivity(toSubCats);
        });
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        TextView catDescription;
        ConstraintLayout item;
        CircleImageView image;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.item_name);
            catDescription = itemView.findViewById(R.id.item_description);
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

        for (char c : imageChars) {
            if (c == ' ')
                continue;
            if (c == '+') {
                image += 'p';
                continue;
            }
            if (c == '#') {
                image += "sharp";
                continue;
            }
            image += c;
        }
        image += "_icon";
        return image;
    }
}
