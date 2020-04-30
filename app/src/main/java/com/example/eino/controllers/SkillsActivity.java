package com.example.eino.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.eino.R;
import com.example.eino.models.Categories;
import com.example.eino.models.Skill;
import com.example.eino.models.SkillPatch;
import com.example.eino.models.User;
import com.example.eino.models.adapters.SkillAdapter;
import com.example.eino.models.data_sources.CategoryDataSource;
import com.example.eino.models.data_sources.UserDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillsActivity extends AppCompatActivity implements UserDataSource.UserDataSourceDelegate {

    private static final String TAG = "SkillsActivity";

    EditText searchField;
    RecyclerView skillsRecycler;

    ArrayList<Skill> allSkills;
    Set<String> currentUserSkills;
    SkillAdapter adapter;
    CircleImageView addButton;

    UserDataSource userDataSource;
    CategoryDataSource categoryDataSource;


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        skillsRecycler = findViewById(R.id.skills_recycler);
        userDataSource = new UserDataSource(this);
        categoryDataSource = new CategoryDataSource();
        allSkills = Categories.getInstance().getSkills();
        sharedPreferences = getSharedPreferences(LogInActivity.SHARED_PREFS, MODE_PRIVATE);
        currentUserSkills = sharedPreferences.getStringSet(UserDataSource.SKILLSET_SP_KEY, null);
        initializeUserSkills();
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(addListener);
        searchField = findViewById(R.id.search_field);
        searchField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);
        searchField.addTextChangedListener(searchChange);
        skillsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        skillsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeUserSkills() {
        Set<String> userSkills = sharedPreferences.getStringSet(UserDataSource.SKILLSET_SP_KEY, null);
        if (userSkills == null) {
            userDataSource.getUserByID(sharedPreferences.getString(LogInActivity.ID_SP_KEY, ""));
        } else {
            currentUserSkills = userSkills;
            checkUserSkills();
        }

    }

    private void checkUserSkills() {
        for (Skill skill : allSkills) {
            if (currentUserSkills.contains(skill.getName()))
                skill.setChecked(true);
        }
        adapter = new SkillAdapter(allSkills, new ArrayList<>(currentUserSkills), this);
        skillsRecycler.setAdapter(adapter);
    }

    TextWatcher searchChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            filter(s.toString());
        }
    };


    @Override
    public void userFetched(User user) {
        currentUserSkills = new HashSet<>(user.getSkills());
        sharedPreferences.edit().putStringSet(UserDataSource.SKILLSET_SP_KEY, new HashSet<>(user.getSkills())).commit();
        checkUserSkills();
    }

    View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = getSharedPreferences(LogInActivity.SHARED_PREFS, MODE_PRIVATE).getString(LogInActivity.EMAIL_SP_KEY, "");
            ArrayList<String> skills = ((SkillAdapter) skillsRecycler.getAdapter()).getSelectedSkills();
            Log.d(TAG, "onClick: PATCHING " + skills.size() + " SKILLS TO USER: " + email);
            userDataSource.addUserSkills(email, new SkillPatch(skills));
            updateCategories();
            sharedPreferences.edit().putStringSet(UserDataSource.SKILLSET_SP_KEY, new HashSet<>(skills)).commit();
            finish();
        }
    };

    private void updateCategories() {
        Log.d(TAG, "old list: " + currentUserSkills.toString());
        Set<String> oldList = currentUserSkills;
        ArrayList<String> newList = ((SkillAdapter) skillsRecycler.getAdapter()).getSelectedSkills();

        ArrayList<String> newSkills = new ArrayList<>(newList);
        newSkills.removeAll(oldList);
        ArrayList<String> newCategorySet = getCategorySet(newSkills);

        ArrayList<String> oldSkills = new ArrayList<>(oldList);
        oldSkills.removeAll(newList);
        ArrayList<String> oldCategorySet = getCategorySet(oldSkills);

        String userID = sharedPreferences.getString(LogInActivity.ID_SP_KEY, "");

        Thread categoryUpdater = new Thread(() -> {

            for (String skill : newCategorySet) {
                categoryDataSource.addUserToCategory(skill, userID);
            }
            for (String skill : oldCategorySet) {
                categoryDataSource.removeUserFromCategory(skill, userID);
            }
        });
        categoryUpdater.start();
    }

    public ArrayList<String> getCategorySet(ArrayList<String> subCategory) {
        Set<String> subCatSet = new HashSet<>();

        for (String subCat : subCategory) {
            subCatSet.add(getCategory(subCat));
        }
        Log.d(TAG, "getCategorySet: SUBCATEGORIES RECEIVED: " + subCategory.size());
        Log.d(TAG, "getCategorySet: CATEGORIES RETURNED: " + subCatSet.size());
        return new ArrayList<>(subCatSet);
    }


    public String getCategory(String subCategory) {
        subCategory = subCategory.toLowerCase();

        String[] categories = {"Programming", "Cooking", "Gardening", "Art", "Science"};

        String[] programmingArr = {"java", "c", "c++", "python", "ruby", "html", "php", "c#", "swift", "javascript"};
        String[] cookingArr = {"savory", "sweet", "baking"};
        String[] gardeningArr = {"herbs gardening", "flower gardening"};
        String[] artArr = {"anatomy", "digital art", "photoshop", "illustrator", "oil painting"};
        String[] scienceArr = {"physics", "chemistry", "mathematics"};

        String[][] subCategoryArr = {programmingArr, cookingArr, gardeningArr, artArr, scienceArr};

        HashMap<String, String> categoryMap = new HashMap<String, String>();
        int numOfRows = subCategoryArr.length;
        String sub = null;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < subCategoryArr[i].length; j++) {
                sub = subCategoryArr[i][j];
                categoryMap.put(sub, categories[i]);
            }
        }
        return categoryMap.get(subCategory);
    }

    public void filter(String text) {
        ArrayList<Skill> filtered = new ArrayList<>();
        for (Skill skill : allSkills) {
            if (skill.getName().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(skill);
            }
        }
        adapter.filteredList(filtered);
    }
}
