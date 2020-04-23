package com.example.eino.controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.eino.R;

public class SignupActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 111;

    EditText nameField;
    EditText surnameField;
    EditText emailField;
    EditText passwordField;
    EditText phoneNumberField;
    EditText firstInfoField;
    EditText secondInfoField;

    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initilizeViews();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), "Pick a profile picture"), GALLERY_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && data != null && resultCode == RESULT_OK) {
            Uri imageData = data.getData();
            profileImage.setImageURI(imageData);

        }
    }

    private void initilizeViews() {
        nameField = (EditText) findViewById(R.id.nameEditText);
        nameField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, 0, 0);

        surnameField = (EditText) findViewById(R.id.surnameEditText);
        surnameField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, 0, 0);

        emailField = (EditText) findViewById(R.id.emailEditText);
        emailField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email_black_24dp, 0, 0, 0);

        passwordField = (EditText) findViewById(R.id.passwordEditText);
        passwordField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_black_24dp, 0, 0, 0);

        phoneNumberField = (EditText) findViewById(R.id.phoneNumberEditText);
        phoneNumberField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_phone_black_24dp, 0, 0, 0);

        firstInfoField = (EditText) findViewById(R.id.firstInfoEditText);
        secondInfoField = (EditText) findViewById(R.id.secondInfoEditText);

        profileImage = findViewById(R.id.profile_image);
    }
}