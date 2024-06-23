package com.example.pizzaapplication.data.api;

import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Request.CustomerOrderRequestModel;
import com.example.pizzaapplication.data.model.Request.ProfileRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;
import com.example.pizzaapplication.data.model.Response.SizeModel;
import com.example.pizzaapplication.data.model.Response.SizeResponseModel;
import com.example.pizzaapplication.data.model.Response.ToppingModel;
import com.example.pizzaapplication.data.model.Response.ToppingResponseModel;
import com.example.pizzaapplication.data.model.User;

import java.util.List;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    //Get profile
    @GET("users/profile?id=1")
    Call<ProfileResponseModel> getProfile();

    @Multipart
    @PUT("users/profile")
    Call<ApiResponse> updateProfile(
            @Part("id") RequestBody id,
            @Part("first_name") RequestBody firstName,
            @Part("last_name") RequestBody lastName,
            @Part("date_of_birth") RequestBody dateOfBirth,
            @Part("address") RequestBody address,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part profilePic
    );
}