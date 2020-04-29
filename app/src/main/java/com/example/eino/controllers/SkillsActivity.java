package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.eino.models.adapters.SkillAdapter;
import com.example.eino.models.data_sources.UserDataSource;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillsActivity extends AppCompatActivity {

    private static final String TAG = "SkillsActivity";

    EditText searchField;
    RecyclerView skillsRecycler;
    ArrayList<Skill> allSkills;
    SkillAdapter adapter;
    CircleImageView addButton;

    UserDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        dataSource = new UserDataSource();
        allSkills = Categories.getInstance().getSkills();
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(addListener);
        searchField = findViewById(R.id.search_field);
        searchField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);
        searchField.addTextChangedListener(searchChange);
        skillsRecycler = findViewById(R.id.skills_recycler);
        adapter = new SkillAdapter(allSkills, this);
        skillsRecycler.setAdapter(adapter);
        skillsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        skillsRecycler.setLayoutManager(new LinearLayoutManager(this));
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


    View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = getSharedPreferences(LogInActivity.SHARED_PREFS, MODE_PRIVATE).getString(LogInActivity.EMAIL_SP_KEY, "");
            ArrayList<String> skills = ((SkillAdapter) skillsRecycler.getAdapter()).getSelectedSkills();
            Log.d(TAG, "onClick: PATCHING " + skills.size() + " SKILLS TO USER: " + email);
            dataSource.addUserSkills(email, new SkillPatch(skills));
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
