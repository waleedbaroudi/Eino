package com.example.eino.controllers.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eino.R;
import com.example.eino.controllers.LogInActivity;
import com.example.eino.controllers.MainActivity;
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

    ImageButton logoutButton;

    SharedPreferences sharedPreferences;

    public MyProfileFragment(User currentUser) { //TODO: CHANGE THIS TO TAKE A USER INSTEAD OF AN ID.
        this.currentUser = currentUser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_profile, container, false);
        name = view.findViewById(R.id.name_label);
        username = view.findViewById(R.id.username_label);
        logoutButton = view.findViewById(R.id.sign_out_button);
        sharedPreferences = getContext().getSharedPreferences(LogInActivity.SHARED_PREFS, Context.MODE_PRIVATE);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
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

    private void showAlertDialog() {
        boolean result = false;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage("Are you sure you want to log out?");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        alertDialog.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                editor.clear();
                editor.commit();
                Toast.makeText(getContext(), "Logged out!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setNegativeButton("Cancel", null);
        alertDialog.show();
    }
}
