package com.example.eino.controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eino.R;
import com.example.eino.models.User;
import com.example.eino.models.data_sources.UserDataSource;

public class SignupActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 111;

    UserDataSource dataSource;

    Uri imageData;

    EditText nameField;
    EditText surnameField;
    EditText emailField;
    EditText passwordField;
    EditText phoneNumberField;
    EditText firstInfoField;
    EditText secondInfoField;

    Spinner firstInfoType;
    Spinner secondInfoType;

    ImageView profileImage;

    Button signUpButton;

    private boolean signedUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeViews();
        initializeListeners();
        dataSource = new UserDataSource();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (signedUp)
            finish();
    }

    private void initializeListeners() {
        profileImage.setOnClickListener(v -> startActivityForResult(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), "Pick a profile picture"), GALLERY_REQUEST_CODE));
        signUpButton.setOnClickListener(signUpListener);
    }

    private View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (nameField.getText().toString().isEmpty() ||
                    surnameField.getText().toString().isEmpty() || emailField.getText().toString().isEmpty() || passwordField.getText().toString().isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill in all mandatory fields", Toast.LENGTH_SHORT).show();
                return;
            }
            dataSource.addUser(makeUser());
            signedUp = true;
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
        }
    };


    private User makeUser() {
        User user = new User();
        user.setDisplayName(nameField.getText().toString() + " " + surnameField.getText().toString());
        user.setEmail(emailField.getText().toString());
        user.setPassword(passwordField.getText().toString());
        if (!phoneNumberField.getText().toString().isEmpty())
            user.addContactInfo("Phone", phoneNumberField.getText().toString());
        if (!firstInfoField.getText().toString().isEmpty())
            user.addContactInfo(firstInfoType.getSelectedItem().toString(), firstInfoField.getText().toString());
        if (!secondInfoField.getText().toString().isEmpty())
            user.addContactInfo(secondInfoType.getSelectedItem().toString(), secondInfoField.getText().toString());
        if (imageData != null)
            user.setProfilePicture(imageData.toString());
        return user;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && data != null && resultCode == RESULT_OK) {
            imageData = data.getData();
            profileImage.setImageURI(imageData);

        }
    }

    private void initializeViews() {
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

        firstInfoType = findViewById(R.id.firstType);
        secondInfoType = findViewById(R.id.secondType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.contact_info_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstInfoType.setAdapter(adapter);
        secondInfoType.setAdapter(adapter);


        profileImage = findViewById(R.id.profile_image);

        signUpButton = findViewById(R.id.sign_up_button);
    }
}