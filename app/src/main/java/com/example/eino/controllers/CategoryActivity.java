package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.eino.models.Category;
import com.example.eino.models.adapters.MainCategoryAdapter;
import com.example.eino.R;
import com.example.eino.models.data_sources.CategoryDataSource;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryDataSource.CategoryDataSourceDelegate {
    RecyclerView categoryRecycler;
    CategoryDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        dataSource = new CategoryDataSource();
        dataSource.setDelegate(this);
        dataSource.fetchCategories();
        categoryRecycler = findViewById(R.id.category_recyclerview);

    }

    @Override
    public void categoriesFetched(ArrayList<Category> categories) {
        MainCategoryAdapter adapter = new MainCategoryAdapter(categories);
        categoryRecycler.setAdapter(adapter);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void fetchFailure() {
        Toast.makeText(CategoryActivity.this, "Could not load categories", Toast.LENGTH_LONG).show();
    }
}