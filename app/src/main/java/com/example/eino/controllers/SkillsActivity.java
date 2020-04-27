package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.eino.R;
import com.example.eino.models.Categories;
import com.example.eino.models.adapters.SkillAdapter;

import java.util.ArrayList;

public class SkillsActivity extends AppCompatActivity {

    EditText searchField;
    RecyclerView skillsRecycler;
    ArrayList<String> allSkills;
    SkillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        allSkills = Categories.getInstance().getAllsubcats();
        searchField = findViewById(R.id.search_field);
        searchField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);
        searchField.addTextChangedListener(searchChange);
        skillsRecycler = findViewById(R.id.skills_recycler);
        adapter = new SkillAdapter(allSkills);
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

    public void filter(String text) {
        ArrayList<String> filtered = new ArrayList<>();
        for (String skill : allSkills) {
            if (skill.toLowerCase().contains(text.toLowerCase())) {
                filtered.add(skill);
            }
        }
        adapter.filteredList(filtered);
    }
}
