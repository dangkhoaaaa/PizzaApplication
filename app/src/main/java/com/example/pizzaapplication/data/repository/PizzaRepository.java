package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class PizzaRepository {

    private ApiService apiService;

    public PizzaRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getPizzas(Callback<PizzaResponseModel> callback) {
        Call<PizzaResponseModel> call = apiService.getPizza();
        call.enqueue(callback);
    }
}
