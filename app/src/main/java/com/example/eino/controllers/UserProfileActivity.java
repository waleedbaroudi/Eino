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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eino.R;
import com.example.eino.models.User;
import com.example.eino.models.data_sources.UserDataSource;
import com.nex3z.flowlayout.FlowLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity implements UserDataSource.UserDataSourceDelegate {

    ConstraintLayout userLayout;
    ConstraintSet spreadSet = new ConstraintSet();

    TextView nameLabel;
    TextView usernameLabel;

    CardView infoCard;
    CircleImageView imageView;
    ImageView topImage;
    FlowLayout skillsLayout;

    ProgressBar userLoadingIndicator;

    User currentUser;
    UserDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        dataSource = new UserDataSource(this);
        String currentID = getIntent().getExtras().getString("targetUserID");
        dataSource.getUserByID(currentID);

        spreadSet.clone(this, R.layout.activity_user_profile);
    }


    @Override
    public void userFetched(User user) {
        currentUser = user;
        initializeViews();
    }

    private void initializeViews() {
        nameLabel = findViewById(R.id.name_label);
        nameLabel.setText(currentUser.getDisplayName());
        usernameLabel = findViewById(R.id.username_label);
        usernameLabel.setText(currentUser.getUsername());

        userLoadingIndicator = findViewById(R.id.user_loading_indicator);
        userLoadingIndicator.setVisibility(View.GONE);

        userLayout = findViewById(R.id.user_layout);
        skillsLayout = findViewById(R.id.skillsLayout);
        for (String skill : currentUser.getSkills()) {
            createSkill(skill);
        }

        infoCard = findViewById(R.id.cardView);
        infoCard.setOnClickListener(v -> {
            spreadSet.connect(R.id.name_label, ConstraintSet.TOP, R.id.user_layout, ConstraintSet.TOP, 150);
            spreadSet.applyTo(userLayout);
            imageView.setVisibility(imageView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            topImage.setVisibility(topImage.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        imageView = findViewById(R.id.circleImageView);
        topImage = findViewById(R.id.imageView);
    }


    public void createSkill(String skillName) {
        TextView skill = new TextView(UserProfileActivity.this);
        skill.setLayoutParams(new FlowLayout.LayoutParams(
                FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT
        ));
        skill.setBackgroundResource(R.drawable.skill_tag);
        int horizontalPadding = (int) (12 * getResources().getDisplayMetrics().density + 0.5f);
        int verticalPadding = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
        skill.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        skill.setText(skillName);
        skill.setTextColor(Color.WHITE);
        skill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
        skillsLayout.addView(skill);
    }
}
