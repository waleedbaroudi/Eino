package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.eino.R;
import com.example.eino.models.adapters.SkillAdapter;

public class SkillsActivity extends AppCompatActivity {

    EditText searchField;
    RecyclerView skillsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        searchField = findViewById(R.id.search_field);
        skillsRecycler=findViewById(R.id.skills_recycler);
        skillsRecycler.setAdapter(new SkillAdapter());
        skillsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
