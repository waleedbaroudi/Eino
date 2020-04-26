package com.example.eino.models;

import com.example.eino.models.data_sources.CategoryDataSource;

import java.util.ArrayList;

public class Categories implements CategoryDataSource.CategoryDataSourceDelegate {

    private static Categories categoriesInstance;
    CategoryDataSource dataSource = new CategoryDataSource();
    private ArrayList<Category> categories;

    public static Categories getInstance() {
        if (categoriesInstance == null)
            categoriesInstance = new Categories();
        return categoriesInstance;
    }

    private Categories() {
        dataSource.setDelegate(this);
        dataSource.fetchCategories();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<String> getAllsubcats() {
        ArrayList<String> subcats = new ArrayList<>();
        for (Category category : categories) {
            subcats.addAll(category.getSubCategories());
        }
        return subcats;
    }
    public ArrayList<String> getSubcategories(Category category) {
        ArrayList<String> subcats = new ArrayList<>(category.getSubCategories());
        return subcats;
    }

    @Override
    public void categoriesFetched(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void fetchFailure() {

    }
}
