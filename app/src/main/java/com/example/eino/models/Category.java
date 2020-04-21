package com.example.eino.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category {
    @SerializedName("subCategories")
    @Expose
    private ArrayList<String> subCategories;

    @SerializedName("type")
    @Expose
    private String type;

    private int numOfPeople=0;

    public Category(String type, ArrayList<String> subCategories, int numOfPeople) {
        this.type = type;
        this.subCategories = subCategories;
//        this.numOfPeople = numOfPeople;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getSubCategories() {
        return subCategories;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public void addSubCategory(String category){
        subCategories.add(category);
    }
}
