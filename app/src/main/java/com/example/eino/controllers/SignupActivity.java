package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.eino.R;

public class SignupActivity extends AppCompatActivity {

    EditText nameField;
    EditText surnameField;

    EditText emailField;
    EditText passwordField;

    EditText phoneNumberField;
    EditText firstInfoField;

    EditText secondInfoField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameField = (EditText) findViewById(R.id.nameEditText);
        nameField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp,0,0,0);

        surnameField = (EditText) findViewById(R.id.surnameEditText);
        surnameField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp,0,0,0);

        emailField = (EditText) findViewById(R.id.emailEditText);
        emailField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email_black_24dp,0,0,0);

        passwordField = (EditText) findViewById(R.id.passwordEditText);
        passwordField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_black_24dp,0,0,0);

        phoneNumberField = (EditText) findViewById(R.id.phoneNumberEditText);
        phoneNumberField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_phone_black_24dp,0,0,0);

        firstInfoField = (EditText) findViewById(R.id.firstInfoEditText);
        secondInfoField = (EditText) findViewById(R.id.secondInfoEditText);

    }
}