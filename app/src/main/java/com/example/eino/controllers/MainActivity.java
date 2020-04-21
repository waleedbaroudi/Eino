package com.example.eino.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.example.eino.R;
import com.example.eino.controllers.fragments.CategoryFragment;
import com.example.eino.controllers.fragments.MyProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FrameLayout fragCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_bar);
        fragCont = findViewById(R.id.frag_container);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new CategoryFragment()).commit();
        bottomNav.setOnNavigationItemSelectedListener(listener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener  listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.categories_item :
                    selectedFragment = new CategoryFragment();
                    break;
                case R.id.profile_item :
                    selectedFragment = new MyProfileFragment();
                    break;
            }
            TransitionManager.beginDelayedTransition(fragCont);
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, selectedFragment).commit();

            return true;
        }
    };
}
