package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.eino.R;
import com.example.eino.models.Categories;
import com.example.eino.models.Skill;
import com.example.eino.models.SkillPatch;
import com.example.eino.models.User;
import com.example.eino.models.adapters.SkillAdapter;
import com.example.eino.models.data_sources.UserDataSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillsActivity extends AppCompatActivity implements UserDataSource.UserDataSourceDelegate {

    private static final String TAG = "SkillsActivity";

    EditText searchField;
    RecyclerView skillsRecycler;

    ArrayList<Skill> allSkills;
    Set<String> currentUserSkills;
    SkillAdapter adapter;
    CircleImageView addButton;

    UserDataSource dataSource;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        skillsRecycler = findViewById(R.id.skills_recycler);
        dataSource = new UserDataSource(this);
        allSkills = Categories.getInstance().getSkills();
        sharedPreferences = getSharedPreferences(LogInActivity.SHARED_PREFS, MODE_PRIVATE);
        currentUserSkills = sharedPreferences.getStringSet(UserDataSource.SKILLSET_SP_KEY, null);
        initializeUserSkills();
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(addListener);
        searchField = findViewById(R.id.search_field);
        searchField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);
        searchField.addTextChangedListener(searchChange);
        skillsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        skillsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeUserSkills() {
        Set<String> userSkills = sharedPreferences.getStringSet(UserDataSource.SKILLSET_SP_KEY, null);
        if (userSkills == null) {
            dataSource.getUserByID(sharedPreferences.getString(LogInActivity.ID_SP_KEY, ""));
        } else {
            currentUserSkills = userSkills;
            checkUserSkills();
        }

    }

    private void checkUserSkills() {
        for (Skill skill : allSkills) {
            if (currentUserSkills.contains(skill.getName()))
                skill.setChecked(true);
        }
        adapter = new SkillAdapter(allSkills, new ArrayList<>(currentUserSkills), this);
        skillsRecycler.setAdapter(adapter);
    }

    TextWatcher searchChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            filter(s.toString());
        }
    };


    @Override
    public void userFetched(User user) {
        currentUserSkills = new HashSet<>(user.getSkills());
        sharedPreferences.edit().putStringSet(UserDataSource.SKILLSET_SP_KEY, new HashSet<>(user.getSkills())).commit();
        checkUserSkills();
    }

    View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = getSharedPreferences(LogInActivity.SHARED_PREFS, MODE_PRIVATE).getString(LogInActivity.EMAIL_SP_KEY, "");
            ArrayList<String> skills = ((SkillAdapter) skillsRecycler.getAdapter()).getSelectedSkills();
            Log.d(TAG, "onClick: PATCHING " + skills.size() + " SKILLS TO USER: " + email);
            dataSource.addUserSkills(email, new SkillPatch(skills));
            sharedPreferences.edit().putStringSet(UserDataSource.SKILLSET_SP_KEY, new HashSet<>(skills)).commit();
            finish();
        }
    };

    public void filter(String text) {
        ArrayList<Skill> filtered = new ArrayList<>();
        for (Skill skill : allSkills) {
            if (skill.getName().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(skill);
            }
        }
        adapter.filteredList(filtered);
    }
}
