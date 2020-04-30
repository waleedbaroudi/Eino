package com.example.eino.controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eino.R;
import com.example.eino.models.User;
import com.example.eino.models.data_sources.UserDataSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity implements UserDataSource.UserDataSourceDelegate {
    private static final int GALLERY_REQUEST_CODE = 111;

    UserDataSource dataSource;

    Uri imageData;

    private ArrayList<String> existingEmails;

    SharedPreferences sharedPreferences;

    private boolean registered = false;

    EditText nameField;
    EditText surnameField;
    EditText emailField;
    EditText passwordField;
    EditText phoneNumberField;
    EditText firstInfoField;
    EditText secondInfoField;

    ProgressBar progressBar;

    Spinner firstInfoType;
    Spinner secondInfoType;

    ImageView profileImage;

    ConstraintLayout mainLayout;

    Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sharedPreferences = getSharedPreferences(LogInActivity.SHARED_PREFS, MODE_PRIVATE);
        dataSource = new UserDataSource(this);
        dataSource.setDelegate(this);
        initializeViews();
        progressBar.setVisibility(View.GONE);
        initializeListeners();
        existingEmails = getIntent().getExtras().getStringArrayList("existingEmails");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean(LogInActivity.LOGGEDIN_SP_KEY, false))
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }

    private void initializeListeners() {
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mainLayout, "Adding a profile picture is currently not supported", Snackbar.LENGTH_LONG).show();
            }
        });
//        profileImage.setOnClickListener(v -> startActivityForResult(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), "Pick a profile picture"), GALLERY_REQUEST_CODE));
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
            if (!emailField.getText().toString().contains("@")){
                Toast.makeText(SignupActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            if (existingEmails == null)
                dataSource.fetchUsers();
            else
                usersFetched(null);
        }
    };

    @Override
    public void usersFetched(ArrayList<User> users) {
        if (existingEmails == null)
            existingEmails = dataSource.getEmails(users);
        if (existingEmails.contains(emailField.getText().toString())) {
            Toast.makeText(this, "a user with the given email already exists", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        dataSource.addUser(makeUser());
    }

    @Override
    public void userAdded(boolean result, User createdUser) {
        progressBar.setVisibility(View.GONE);
        if (result) {
            Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show();
            saveUserData(createdUser.getID());
            startActivity(new Intent(SignupActivity.this, SkillsActivity.class));
        } else
            Toast.makeText(this, "failed to register new user", Toast.LENGTH_SHORT).show();
    }

    private void saveUserData(String ID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LogInActivity.EMAIL_SP_KEY, emailField.getText().toString());
        editor.putString(LogInActivity.PASSWORD_SP_KEY, emailField.getText().toString());
        editor.putString(LogInActivity.ID_SP_KEY, ID);
        editor.putBoolean(LogInActivity.LOGGEDIN_SP_KEY, true);
        editor.commit();
    }

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

        mainLayout = (ConstraintLayout) findViewById(R.id.signup_constraint);

        firstInfoType = findViewById(R.id.firstType);
        secondInfoType = findViewById(R.id.secondType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.contact_info_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstInfoType.setAdapter(adapter);
        secondInfoType.setAdapter(adapter);

        progressBar = findViewById(R.id.signupProgressBar);

        profileImage = findViewById(R.id.profile_image);

        signUpButton = findViewById(R.id.sign_up_button);
    }

}