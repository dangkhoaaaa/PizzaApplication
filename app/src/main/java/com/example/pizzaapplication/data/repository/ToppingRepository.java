package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Response.ToppingModel;
import com.example.pizzaapplication.data.model.Response.ToppingResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ToppingRepository {

    private ApiService apiService;

    public ToppingRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getToppings(Callback<ToppingResponseModel> callback) {
        Call<ToppingResponseModel> call = apiService.getToppings();
        call.enqueue(callback);
    }
}
