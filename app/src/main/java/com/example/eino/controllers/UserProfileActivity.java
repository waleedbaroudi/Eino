package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eino.R;
import com.nex3z.flowlayout.FlowLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    ConstraintLayout userLayout;
    ConstraintSet spreadSet = new ConstraintSet();

    CardView infoCard;
    CircleImageView imageView;
    ImageView topImage;
    FlowLayout skillsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userLayout = findViewById(R.id.user_layout);
        infoCard = findViewById(R.id.cardView);
        skillsLayout = findViewById(R.id.skillsLayout);
        spreadSet.clone(this, R.layout.activity_user_profile);
        imageView = findViewById(R.id.circleImageView);
        topImage = findViewById(R.id.imageView);
        infoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spreadSet.connect(R.id.name_label, ConstraintSet.TOP, R.id.user_layout, ConstraintSet.TOP, 150);
                spreadSet.applyTo(userLayout);
                imageView.setVisibility(imageView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                topImage.setVisibility(topImage.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
    }


    public void createSkill() {
        TextView skill = new TextView(UserProfileActivity.this);
        skill.setLayoutParams(new FlowLayout.LayoutParams(
                FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT
        ));
        skill.setBackgroundResource(R.drawable.skill_tag);
        int horizontalPadding = (int) (12 * getResources().getDisplayMetrics().density + 0.5f);
        int verticalPadding = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
        skill.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        skill.setText("New Skill");
        skill.setTextColor(Color.WHITE);
        skill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
        skillsLayout.addView(skill);
    }
}
