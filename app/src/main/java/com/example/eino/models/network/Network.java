package com.example.eino.models.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Network network = null;
    Retrofit retrofit;
    DataAPI dataAPI;
    public static final String BASE_URL = "https://eino-275710.ey.r.appspot.com"; // TODO: ADD BASE URL
    private Network() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        dataAPI = retrofit.create(DataAPI.class);

    }

    public static Network getInstance() {
        if (network == null)
            network = new Network();
        return network;
    }

    public DataAPI getDataAPI() {
        return dataAPI;
    }
}
