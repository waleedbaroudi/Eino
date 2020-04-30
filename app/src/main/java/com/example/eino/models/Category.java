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

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("description")
    @Expose
    private String description;

    public Category(String type, ArrayList<String> subCategories, int numOfPeople, String id) {
        this.type = type;
        this.subCategories = subCategories;
        this.id = id;
//        this.numOfPeople = numOfPeople;
    }


    public String getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addSubCategory(String category) {
        subCategories.add(category);
    }
}
