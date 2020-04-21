package com.example.eino.models.network;


import com.example.eino.models.Category;
import com.example.eino.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DataAPI {
    @Headers("Content-type: application/json") //TODO: check later
    @GET("users") //TODO: ADD SUB URL
    Call<ArrayList<Category>> getCategories();

    @GET("INSERT-SUB-URL") //TODO: ADD SUB URL
    Call<ArrayList<User>> getUsers();
}
