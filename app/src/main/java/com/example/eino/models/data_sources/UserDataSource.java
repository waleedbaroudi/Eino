package com.example.eino.models.data_sources;

import android.util.Log;

import com.example.eino.models.User;
import com.example.eino.models.network.Network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataSource {
    private static final String TAG = "UserDataSource";
    UserDataSourceDelegate delegate;
    Network network = Network.getInstance();

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

    public void fetchUsers(String Category) {

    }

    public void addUser(User user) {
        Call<User> postUser = network.getDataAPI().postUser(user);
        postUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onPostResponse: posting terminated with code: " + response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onPostFailure: posting failed", t);
            }
        });

    }

    public boolean validateUser(ArrayList<User> users, String email, String password) {
        User found = null;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                found = user;
                break;
            }
        }
        if (found == null)
            return false;
        if (!found.getPassword().equals(password))
            return false;
        return true;
    }

    public ArrayList<String> getEmails(ArrayList<User> users){
        ArrayList<String> emails = new ArrayList<>();
        for(User user : users){
            emails.add(user.getEmail());
        }
        return  emails;
    }

    public void setDelegate(UserDataSourceDelegate delegate) {
        this.delegate = delegate;
    }

    public interface UserDataSourceDelegate {
        default void usersFetched(ArrayList<User> users) {
            Log.d(TAG, "usersFetched: DELEGATE NOT SET");
        }

        default void fetchFailure(Throwable t) {
            Log.d(TAG, "fetchFailure: DELEGATE NOT SET");
        }
    }


}
