package com.example.eino.models.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eino.R;
import com.example.eino.models.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    ArrayList<User> users;
    String selectedSkill = "";

    public UsersAdapter(ArrayList<User> users, String selectedSkill) {
        this.users = users;
        this.selectedSkill = selectedSkill;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.userName.setText(users.get(position).getDisplayName());
        holder.userDescription.setText(users.get(position).getDescription(selectedSkill));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        TextView userName;
        TextView userDescription;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.item_image);
            userName = itemView.findViewById(R.id.item_name);
            userDescription = itemView.findViewById(R.id.item_description);
        }
    }
}
