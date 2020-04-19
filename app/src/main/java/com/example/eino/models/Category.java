package com.example.eino.models;

import java.util.ArrayList;

public class Category {
    private String type;
    private ArrayList<Category> subCategories;
    private int numOfPeople;

    public Category(String type, ArrayList<Category> subCategories, int numOfPeople) {
        this.type = type;
        this.subCategories = subCategories;
        this.numOfPeople = numOfPeople;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public void addSubCategory(Category category){
        subCategories.add(category);
    }
}
