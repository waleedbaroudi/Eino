package com.example.eino.models.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.R;
import com.example.eino.controllers.UserProfileActivity;
import com.example.eino.models.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    ArrayList<User> users;
    String selectedSkill = "";
    Context context;


    public UsersAdapter(ArrayList<User> users, String selectedSkill, Context context) {
        this.users = users;
        this.selectedSkill = selectedSkill;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final User current = users.get(position);
        holder.userName.setText(current.getDisplayName());
        holder.userDescription.setText(current.getDescription(selectedSkill));
        holder.itemCard.setOnClickListener(v -> {
            Intent toUser = new Intent(context, UserProfileActivity.class);
            toUser.putExtra("targetUserID", current.getID());
            context.startActivity(toUser);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        TextView userName;
        CardView itemCard;
        TextView userDescription;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.item_image);
            itemCard = itemView.findViewById(R.id.item_card);
            userName = itemView.findViewById(R.id.item_name);
            userDescription = itemView.findViewById(R.id.item_description);
        }
    }
}
