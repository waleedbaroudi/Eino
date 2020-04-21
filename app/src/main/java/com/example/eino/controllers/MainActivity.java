package com.example.eino.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.eino.R;
import com.example.eino.controllers.fragments.CategoryFragment;
import com.example.eino.controllers.fragments.MyProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FrameLayout fragCont;
    private static final String TAG = "MainActivity";
    private boolean onMyProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    selectedFragment = new CategoryFragment();
                    onMyProfile = false;
                    break;
                case R.id.profile_item:
                    selectedFragment = new MyProfileFragment();
                    onMyProfile = true;
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (onMyProfile)
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.frag_container, selectedFragment);
            else
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frag_container, selectedFragment);
            transaction.commit();
            return true;
        }
    };
}
