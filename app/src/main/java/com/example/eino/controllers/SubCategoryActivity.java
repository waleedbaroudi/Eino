package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.eino.R;
import com.example.eino.models.Category;
import com.example.eino.models.adapters.SubCategoryAdapter;
import com.example.eino.models.data_sources.CategoryDataSource;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {
    RecyclerView subCatRecycler;
    CategoryDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        subCatRecycler = findViewById(R.id.subcat_recycler);
        ArrayList<String> subcats = getIntent().getExtras().getStringArrayList("subcategories");
        SubCategoryAdapter adapter = new SubCategoryAdapter(subcats);
        subCatRecycler.setAdapter(adapter);
        subCatRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
