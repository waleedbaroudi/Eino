package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridLayout;
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

    GridLayout contactLayout;
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

        contactLayout = findViewById(R.id.contact_layout);

        for (User.ContactInfo info : currentUser.getContactInfo()) {
            createContactInfo(info);
        }

        infoCard = findViewById(R.id.cardView);
        infoCard.setOnClickListener(v -> {
            spreadSet.connect(R.id.name_label, ConstraintSet.TOP, R.id.user_layout, ConstraintSet.TOP, 150);
            spreadSet.applyTo(userLayout);
            userLoadingIndicator.setVisibility(View.GONE);
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

    public void createContactInfo(User.ContactInfo contactInfo) {
        //ADDS IMAGE
        ImageView infoImage = infoImage = new ImageView(UserProfileActivity.this);
        infoImage.setLayoutParams(new GridLayoutManager.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));


        if(isPhoneNumber(contactInfo.getInfo())){
            infoImage.setImageResource(R.drawable.ic_local_phone_black_24dp);
        }
        else{
            infoImage.setImageResource(R.drawable.ic_email_black_24dp);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.FILL, 4f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 4f));
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            infoImage.setLayoutParams(param);
        }
        contactLayout.addView(infoImage);
        //ADDS THE INFO
        TextView contact = new TextView(UserProfileActivity.this);
        contact.setLayoutParams(new GridLayoutManager.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
        contact.setText(contactInfo.getInfo());
        contact.setTextColor(getResources().getColor(R.color.mainText));
        contact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.FILL, 4f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 4f));
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            contact.setLayoutParams(param);
        }
        contactLayout.addView(contact);
    }
    private boolean isPhoneNumber(String text) {
        char[] chars = text.toCharArray();
        for(Character c : chars) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
