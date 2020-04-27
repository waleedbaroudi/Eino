package com.example.eino.models.network;


import com.example.eino.models.Category;
import com.example.eino.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DataAPI {
    @Headers("Content-type: application/json") //TODO: check later
    @GET("categories")
    Call<ArrayList<Category>> getCategories();

    @GET("categories/{id}")
    Call<Category> getCategories(@Path("id") String categoryID);

    @GET("users")
    Call<ArrayList<User>> getUsers();

    //getting users in a category
    @GET("categories/{type}")
    Call<ArrayList<User>> getUsers(@Path("type") String type);

    @GET("users/{id}")
    Call<User> getUserByID(@Path("id") String id);

    @POST("users")
    Call<User> postUser(@Body User user);

    @PATCH("users/{email}")
    Call<ArrayList<String>> patchSkills(@Path("email") String userEmail, @Body ArrayList<String> skills);
}
