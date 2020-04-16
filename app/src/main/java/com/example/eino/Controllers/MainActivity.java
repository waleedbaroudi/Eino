package com.example.eino.Controllers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.eino.R;
import com.google.android.material.snackbar.Snackbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean loggedIn = false;

    Button signInButton;

    TextView usernameField;
    TextView passwordField;

    ConstraintSet mainSet = new ConstraintSet();
    ConstraintLayout mainLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        usernameField = (TextView) findViewById(R.id.username_field);
        passwordField = (TextView) findViewById(R.id.password_field);
        mainLayout = findViewById(R.id.main_cons_layout);
        startComponentAnimation();
        setupClickListeners();

    }

    public void startComponentAnimation(){
        mainSet.clone(this, R.layout.activity_main_finalset);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Transition spring  = new ChangeBounds();
                spring.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(mainLayout, spring);
                mainSet.applyTo(mainLayout);

            }
        }, 100);
    }
    public void setupClickListeners(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameField.getText().length()==0 || passwordField.getText().length()==0)
                    Toast.makeText(MainActivity.this, "Please fill log in info", Toast.LENGTH_SHORT).show();
                else {
                    loggedIn=true;
                    startActivity(new Intent(MainActivity.this, SignupActivity.class));
                }
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
