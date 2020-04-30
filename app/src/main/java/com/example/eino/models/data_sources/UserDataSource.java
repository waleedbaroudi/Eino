package com.example.eino.models.data_sources;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.eino.controllers.LogInActivity;
import com.example.eino.models.SkillPatch;
import com.example.eino.models.User;
import com.example.eino.models.network.Network;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataSource {
    private static final String TAG = "UserDataSource";
    public static final String SKILLSET_SP_KEY = "userSkills";

    UserDataSourceDelegate delegate;
    Network network = Network.getInstance();
    Context context;
    static SharedPreferences sharedPreferences;

    public UserDataSource(Context context) {
        this.context = context;
        delegate = (UserDataSourceDelegate) context;
        sharedPreferences = context.getSharedPreferences(LogInActivity.SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public void fetchUsers() {
        Call<ArrayList<User>> call = network.getDataAPI().getUsers();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                delegate.usersFetched(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                delegate.fetchFailure(t);
            }
        });
    }

    public void fetchUsers(String category) {
        Call<ArrayList<User>> usersByCategory = network.getDataAPI().getUsers(category);
        usersByCategory.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Log.d(TAG, "onResponse: " + response.body().size() + " users in category: " + category);
                delegate.fetchedUsersByCategory(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public void addUserSkills(String email, SkillPatch skills) {
        Call<SkillPatch> patchSkills = network.getDataAPI().patchSkills(email, skills);
        patchSkills.enqueue(new Callback<SkillPatch>() {
            @Override
            public void onResponse(Call<SkillPatch> call, Response<SkillPatch> response) {
                Log.d(TAG, "onResponse: PATCHED " + skills.getPatchSize() + "skills to user with email: " + email);
            }

            @Override
            public void onFailure(Call<SkillPatch> call, Throwable t) {
                Log.e(TAG, "onFailure: FAILED TO PATCH SKILLS", t);
            }
        });
    }

    public ArrayList<User> filterBySubcategory(ArrayList<User> users, String subCat) {
        ArrayList<User> filtered = new ArrayList<>();
        for (User user : users) {
            if (user.getSkills().contains(subCat))
                filtered.add(user);
        }
        Log.d(TAG, "filterBySubcategory: filtered" + filtered.size() + "users out of " + users.size());
        return filtered;
    }


    public void addUser(User user) {
        Call<User> postUser = network.getDataAPI().postUser(user);
        postUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201) {
                    delegate.userAdded(true);
                    writeUserSkillSet(response.body().getSkills());
                } else
                    delegate.userAdded(false);
                Log.d(TAG, "onPostResponse: posting terminated with code: " + response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                delegate.userAdded(false);
                Log.e(TAG, "onPostFailure: posting failed", t);
            }
        });

    }

    public static void writeUserSkillSet(LinkedList<String> skills) {
        Set<String> skillSet = new HashSet<>(skills);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(SKILLSET_SP_KEY, skillSet);
        editor.commit();
    }

    public void getUserByID(String id) {
        Call<User> getUser = network.getDataAPI().getUserByID(id);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                delegate.userFetched(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public String validateUser(ArrayList<User> users, String email, String password) {
        User found = null;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                found = user;
                break;
            }
        }
        if (found == null)
            return null;
        if (!found.getPassword().equals(password))
            return null;
        return found.getID();
    }

    public ArrayList<String> getEmails(ArrayList<User> users) {
        if (users == null)
            return null;
        ArrayList<String> emails = new ArrayList<>();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }

    public void setDelegate(UserDataSourceDelegate delegate) {
        this.delegate = delegate;
    }

    public interface UserDataSourceDelegate {
        default void usersFetched(ArrayList<User> users) {
            Log.d(TAG, "usersFetched: DELEGATE NOT SET");
        }

        default void userFetched(User user) {
            Log.d(TAG, "usersFetched: DELEGATE NOT SET");
        }

        default void userAdded(boolean result) {

        }

        default void fetchedUsersByCategory(ArrayList<User> users) {
            Log.d(TAG, "fetchedUsersByCategory: DELEGATE NOT SET");
        }

        default void fetchFailure(Throwable t) {
            Log.d(TAG, "fetchFailure: DELEGATE NOT SET");
        }
    }


}
