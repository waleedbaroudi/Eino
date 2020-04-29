package com.example.eino.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SkillPatch {

    @SerializedName("skills")
    @Expose
    ArrayList<String> skills;

    public SkillPatch(ArrayList<String> skills) {
        this.skills = skills;
    }

    public int getPatchSize() {
        return skills.size();
    }
}
