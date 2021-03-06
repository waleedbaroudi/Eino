package com.example.eino.models.data_sources;

import android.util.Log;

import com.example.eino.models.Category;
import com.example.eino.models.network.Network;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDataSource {
    private Network network = Network.getInstance();
    private CategoryDataSourceDelegate delegate;
    private static final String TAG = "CategoryDataSource";

    public void fetchCategories() {
        Call<ArrayList<Category>> call = network.getDataAPI().getCategories();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                Log.d(TAG, "onResponse: DATA FETCHED WITH CODE: " + response.code());
                delegate.categoriesFetched(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Log.e(TAG, "onFailure: FAILED WITH error: ", t);
                delegate.fetchFailure();
            }
        });
    }

    public void addUserToCategory(String category, String ID) {
        Call<Object> addUser = network.getDataAPI().addUserToCategory(category, ID);
        addUser.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG, "onResponse: ADDED SUCCESSFULLY? " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e(TAG, "onFailure: ERROR WHILE ADDING USER ID", t);
            }
        });
    }

    public void removeUserFromCategory(String category, String ID) {
        Call<Object> removeUser = network.getDataAPI().removeUserFromCategory(category, ID);
        removeUser.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG, "onResponse: REMOVED SUCCESSFULLY? " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e(TAG, "onFailure: ERROR WHILE REMOVING USER ID", t);
            }
        });
    }

    public void setDelegate(CategoryDataSourceDelegate delegate) {
        this.delegate = delegate;
    }

    public interface CategoryDataSourceDelegate {
        default void categoriesFetched(ArrayList<Category> categories) {
            Log.e(TAG, "categoriesFetched: ", new IllegalStateException("Delegate not set"));
        }


        default void fetchFailure() {
            Log.e(TAG, "categoriesFetched: ", new IllegalStateException("Delegate not set"));
        }
    }
}
