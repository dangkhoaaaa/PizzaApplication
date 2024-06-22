package com.example.pizzaapplication.data.api;

import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Request.CustomerOrderRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.model.Response.SizeModel;
import com.example.pizzaapplication.data.model.Response.SizeResponseModel;
import com.example.pizzaapplication.data.model.Response.ToppingModel;
import com.example.pizzaapplication.data.model.Response.ToppingResponseModel;
import com.example.pizzaapplication.data.model.User;

import java.util.List;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("pizzas?current-page=1&page-size=3&sort-by-price=true&descending=true")
    Call<PizzaResponseModel> getPizza();

    @GET("Topping")
    Call<ToppingResponseModel> getToppings();

    @GET("sizes")
    Call<SizeResponseModel> getSizes();

    @GET("drinks?current-page=1&page-size=3&sort-by-price=true&descending=true")
    Call<DrinkResponseModel> getDrinks();

    @POST("cus-orders")
    Call<ApiResponse<Integer>> createOrder(@Body CustomerOrderRequestModel orderRequest);

}