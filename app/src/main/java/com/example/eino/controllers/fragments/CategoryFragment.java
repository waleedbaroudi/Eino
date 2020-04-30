package com.example.eino.controllers.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eino.models.Category;
import com.example.eino.models.adapters.MainCategoryAdapter;
import com.example.eino.R;
import com.example.eino.models.adapters.MainCategoryAdapter;
import com.example.eino.models.data_sources.CategoryDataSource;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements CategoryDataSource.CategoryDataSourceDelegate {
    RecyclerView categoryRecycler;
    CategoryDataSource dataSource;
    Context context;

    public CategoryFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category, container, false);
        categoryRecycler = (RecyclerView) view.findViewById(R.id.category_recyclerview);
        dataSource = new CategoryDataSource();
        dataSource.setDelegate(this);
        dataSource.fetchCategories();
        return view;
    }

    @Override
    public void categoriesFetched(ArrayList<Category> categories) {
        MainCategoryAdapter adapter = new MainCategoryAdapter(categories);
        categoryRecycler.setAdapter(adapter);
        categoryRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void fetchFailure() {
        Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_LONG).show();
    }
}
