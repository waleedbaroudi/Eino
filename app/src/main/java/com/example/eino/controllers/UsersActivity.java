package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.eino.R;
import com.example.eino.models.User;
import com.example.eino.models.adapters.UsersAdapter;
import com.example.eino.models.data_sources.UserDataSource;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements UserDataSource.UserDataSourceDelegate {

    RecyclerView usersRecycler;
    UserDataSource dataSource;
    private String selectedSubCat;
    String selectedCategory;

    private static final String TAG = "UsersActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        selectedSubCat = getIntent().getExtras().getString("selectedSubCat");
        selectedCategory = getIntent().getExtras().getString("selectedCategory");
        usersRecycler = findViewById(R.id.usersRecycler);
        dataSource = new UserDataSource(this);
        dataSource.setDelegate(this);
        dataSource.fetchUsers(selectedCategory);
    }

    @Override
    public void fetchedUsersByCategory(ArrayList<User> users) {
        Log.d(TAG, "fetchedUsersByCategory: NUMBER OF USERS: " + users.size());
        ArrayList<User> usersBySubcat = dataSource.filterBySubcategory(users, selectedSubCat);
        Log.d(TAG, "fetchedUsersByCategory: " + users.size() + " users for category: " + selectedCategory);
        usersRecycler.setAdapter(new UsersAdapter(usersBySubcat, selectedSubCat, this));
        usersRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
