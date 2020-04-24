package com.example.eino.controllers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.eino.R;
import com.example.eino.controllers.fragments.CategoryFragment;
import com.example.eino.models.User;
import com.example.eino.models.data_sources.UserDataSource;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LogInActivity extends AppCompatActivity implements UserDataSource.UserDataSourceDelegate {

    private boolean loggedIn = false;

    Button logInButton;
    Button signUpButton;

    TextView usernameField;
    TextView passwordField;

    ConstraintSet mainSet = new ConstraintSet();
    ConstraintLayout mainLayout;

    UserDataSource dataSource;

    ProgressBar progressBar;

    ArrayList<User> existingUsers;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataSource = new UserDataSource();
        dataSource.setDelegate(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        logInButton = (Button) findViewById(R.id.sign_in_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        usernameField = (TextView) findViewById(R.id.username_field);
        usernameField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, 0, 0);
        passwordField = (TextView) findViewById(R.id.password_field);
        passwordField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_black_24dp, 0, 0, 0);
        mainLayout = findViewById(R.id.main_cons_layout);
        startComponentAnimation();
        setupClickListeners();
    }

    public void startComponentAnimation() {
        mainSet.clone(this, R.layout.activity_login_constraint_set);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Transition spring = new ChangeBounds();
                spring.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(mainLayout, spring);
                mainSet.applyTo(mainLayout);

            }
        }, 100);
    }

    public void setupClickListeners() {
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameField.getText().length() == 0 || passwordField.getText().length() == 0) {
                    Toast.makeText(LogInActivity.this, "Please fill log in info", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                if (existingUsers == null)
                    dataSource.fetchUsers();
                else
                    usersFetched(null);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignupActivity.class));
            }
        });
    }

    @Override
    public void usersFetched(ArrayList<User> users) {
        if (existingUsers == null)
            existingUsers = users;
        if (dataSource.validateUser(existingUsers, usernameField.getText().toString(), passwordField.getText().toString())) {
            Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show();
            loggedIn = true;
            existingUsers = null;
            progressBar.setVisibility(View.INVISIBLE);
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Please check your email and password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loggedIn)
            finish();
    }
}
