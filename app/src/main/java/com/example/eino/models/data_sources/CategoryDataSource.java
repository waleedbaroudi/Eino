package com.example.eino.models.data_sources;

import com.example.eino.models.Category;
import com.example.eino.models.network.Network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDataSource {
    private Network network = Network.getInstance();
    private CategoryDataSourceDelegate delegate;

    public void fetchCategories() {
        Call<ArrayList<Category>> call = network.getDataAPI().getCategories();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                delegate.categoriesFetched(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                delegate.fetchFailure();
            }
        });

    }

    public void setDelegate(CategoryDataSourceDelegate delegate) {
        this.delegate = delegate;
    }

    public interface CategoryDataSourceDelegate{
        void categoriesFetched(ArrayList<Category> categories);
        void fetchFailure();
    }
}
