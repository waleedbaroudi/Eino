package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eino.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    ConstraintLayout userLayout;
    ConstraintSet spreadSet = new ConstraintSet();

    CardView infoCard;
    CircleImageView imageView;
    ImageView topImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userLayout = findViewById(R.id.user_layout);
        infoCard = findViewById(R.id.cardView);
        spreadSet.clone(this, R.layout.activity_user_profile);
        imageView = findViewById(R.id.circleImageView);
        topImage = findViewById(R.id.imageView);
        infoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spreadSet.connect(R.id.textView, ConstraintSet.TOP, R.id.user_layout, ConstraintSet.TOP, 150);
                spreadSet.applyTo(userLayout);
                imageView.setVisibility(imageView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                topImage.setVisibility(topImage.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
    }
}
