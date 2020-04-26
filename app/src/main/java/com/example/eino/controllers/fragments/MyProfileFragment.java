package com.example.eino.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eino.R;
import com.example.eino.controllers.SkillsActivity;
import com.example.eino.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileFragment extends Fragment {

    private static final String TAG = "MyProfileFragment";

    CircleImageView profileImage;

    TextView name;
    TextView username;

    User currentUser;

    View view;

    public MyProfileFragment( User currentUser) { //TODO: CHANGE THIS TO TAKE A USER INSTEAD OF AN ID.
        this.currentUser = currentUser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_profile, container, false);
        name = view.findViewById(R.id.name_label);
        username = view.findViewById(R.id.username_label);
        profileImage = view.findViewById(R.id.circleImageView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SkillsActivity.class));
            }
        });
        name.setText(currentUser.getDisplayName());
        username.setText(currentUser.getUsername());
        return view;
    }
}
