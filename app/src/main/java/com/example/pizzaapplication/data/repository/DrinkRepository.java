package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;

import retrofit2.Call;
import retrofit2.Callback;

public class DrinkRepository {

    private ApiService apiService;

    public DrinkRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getDrinks(Callback<DrinkResponseModel> callback) {
        Call<DrinkResponseModel> call = apiService.getDrinks();
        call.enqueue(callback);
    }
}
