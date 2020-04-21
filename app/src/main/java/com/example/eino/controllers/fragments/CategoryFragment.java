package com.example.eino.controllers.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eino.models.MainCategoryAdapter;
import com.example.eino.R;

public class CategoryFragment extends Fragment {
    RecyclerView categoryRecycler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category, container, false);
        categoryRecycler = (RecyclerView) view.findViewById(R.id.category_recyclerview);
        MainCategoryAdapter adapter = new MainCategoryAdapter();
        categoryRecycler.setAdapter(adapter);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
