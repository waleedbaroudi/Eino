package com.example.eino.controllers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.eino.R;

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
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {

    private boolean loggedIn = false;

    Button logInButton;
    Button signUpButton;

    TextView usernameField;
    TextView passwordField;

    ConstraintSet mainSet = new ConstraintSet();
    ConstraintLayout mainLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logInButton = (Button) findViewById(R.id.sign_in_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        usernameField = (TextView) findViewById(R.id.username_field);
        passwordField = (TextView) findViewById(R.id.password_field);
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
//                    Toast.makeText(LogInActivity.this, "Please fill log in info", Toast.LENGTH_SHORT).show();
//                    return;
                }
                loggedIn = true;
                startActivity(new Intent(LogInActivity.this, CategoryActivity.class));

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
    protected void onResume() {
        super.onResume();
        if (loggedIn)
            finish();
    }
}
