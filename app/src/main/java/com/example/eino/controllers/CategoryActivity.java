package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.eino.models.MainCategoryAdapter;
import com.example.eino.R;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView categoryRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryRecycler = (RecyclerView) findViewById(R.id.category_recyclerview);
        MainCategoryAdapter adapter = new MainCategoryAdapter();
        categoryRecycler.setAdapter(adapter);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
