package com.example.pizzaapplication.data.api;

import com.example.pizzaapplication.data.model.User;

import java.util.List;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();
}