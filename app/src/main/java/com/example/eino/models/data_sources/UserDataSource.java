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
    public void fetchUsers(){
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

    public void fetchUsers(String Category){

    }

    public void addUser(User user){

    }


    public void setDelegate(UserDataSourceDelegate delegate) {
        this.delegate = delegate;
    }

    public interface UserDataSourceDelegate{
        default void usersFetched(ArrayList<User> users){
            Log.d(TAG, "usersFetched: DELEGATE NOT SET");
        }

        default void fetchFailure(Throwable t){
            Log.d(TAG, "fetchFailure: DELEGATE NOT SET");
        }
    }


}
