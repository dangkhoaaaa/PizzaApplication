package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Response.OrderHistoryResponseModel;
import com.example.pizzaapplication.share.DataLocalManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class OrderHistoryRepository {
    private ApiService apiService;

    public OrderHistoryRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getOrderHistorys(Callback<List<OrderHistoryResponseModel>> callback) {
        String userId = DataLocalManager.getUserId();
        Call<List<OrderHistoryResponseModel>> call = apiService.getOrderHistory(userId);
        call.enqueue(callback);
    }



}
