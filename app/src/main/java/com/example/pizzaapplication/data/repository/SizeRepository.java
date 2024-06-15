package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Response.SizeModel;
import com.example.pizzaapplication.data.model.Response.SizeResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SizeRepository {

    private ApiService apiService;

    public SizeRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getSizes(Callback<SizeResponseModel> callback) {
        Call<SizeResponseModel> call = apiService.getSizes();
        call.enqueue(callback);
    }
}
