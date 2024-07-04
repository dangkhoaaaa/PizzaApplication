package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Notification;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NotificationRepository {
    private ApiService apiService;

    public NotificationRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getNoti(Callback<List<Notification>> callback) {
        Call<List<Notification>> call = apiService.getNotification();
        call.enqueue(callback);
    }
}
