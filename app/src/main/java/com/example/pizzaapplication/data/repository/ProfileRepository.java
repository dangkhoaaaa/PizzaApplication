package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileRepository {
    private ApiService apiService;

    public ProfileRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getProfile(Callback<ProfileResponseModel> callback) {
        Call<ProfileResponseModel> call = apiService.getProfile();
        call.enqueue(callback);
    }


}
