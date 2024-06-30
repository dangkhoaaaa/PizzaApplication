package com.example.pizzaapplication.data.repository;


import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.User;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;

public class MainRepository {

    private ApiService apiService;

    public MainRepository(ApiService apiService) {
        this.apiService = apiService;
    }

//    public void getUsers(Callback<List<User>> callback) {
//        Call<List<User>> call = apiService.getUsers();
//        call.enqueue(callback);
//    }
}

