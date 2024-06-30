package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Response.OrderDetailResponseModel;

import retrofit2.Call;
import retrofit2.Callback;

public class OrderDetailRepository {
    private ApiService apiService;

    public OrderDetailRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getOrderDetails(String orderID, Callback<OrderDetailResponseModel> callback) {
        Call<OrderDetailResponseModel> call = apiService.getOrderDetails(orderID);
        call.enqueue(callback);
    }
}
