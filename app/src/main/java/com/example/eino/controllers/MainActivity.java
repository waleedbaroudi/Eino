package com.example.eino.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.eino.R;
import com.example.eino.controllers.fragments.CategoryFragment;
import com.example.eino.controllers.fragments.MyProfileFragment;
import com.example.eino.models.User;
import com.example.eino.models.data_sources.UserDataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements UserDataSource.UserDataSourceDelegate {

    UserDataSource dataSource;

    BottomNavigationView bottomNav;
    FrameLayout fragCont;
    private static final String TAG = "MainActivity";
    private boolean onMyProfile = false;

    User currentUser;
    private boolean userReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new UserDataSource(this);
        dataSource.setDelegate(this);

        String userID = getSharedPreferences(LogInActivity.SHARED_PREFS, MODE_PRIVATE).getString(LogInActivity.ID_SP_KEY, "");
        dataSource.getUserByID(userID);
        bottomNav = findViewById(R.id.bottom_bar);
        fragCont = findViewById(R.id.frag_container);
        bottomNav.setOnNavigationItemSelectedListener(listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new CategoryFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.categories_item:
                    if (!onMyProfile)
                        return true;
                    selectedFragment = new CategoryFragment();
                    onMyProfile = false;
                    break;
                case R.id.profile_item:
                    if (onMyProfile)
                        return true;
                    if (!userReady) {
                        Toast.makeText(MainActivity.this, "Preparing you profile, try again in a few moments", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    selectedFragment = new MyProfileFragment(currentUser);
                    onMyProfile = true;
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (onMyProfile)
                transaction.replace(R.id.frag_container, selectedFragment);
            else
                transaction.replace(R.id.frag_container, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent exit = new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(exit);
    }

    @Override
    public void userFetched(User user) {
        currentUser = user;
        userReady = true;
    }
}
