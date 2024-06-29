package com.example.pizzaapplication.data.api;

import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Request.CustomerOrderRequestModel;
import com.example.pizzaapplication.data.model.Request.ProfileRequestModel;
import com.example.pizzaapplication.data.model.Request.RegisterRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;
import com.example.pizzaapplication.data.model.Response.OrderHistoryResponseModel;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;
import com.example.pizzaapplication.data.model.Response.RegisterResponse;
import com.example.pizzaapplication.data.model.Response.SizeModel;
import com.example.pizzaapplication.data.model.Response.SizeResponseModel;
import com.example.pizzaapplication.data.model.Response.TokenResponse;
import com.example.pizzaapplication.data.model.Response.ToppingModel;
import com.example.pizzaapplication.data.model.Response.ToppingResponseModel;
import com.example.pizzaapplication.data.model.User;

import java.util.List;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<DrinkResponseModel> getDrinks(@Header("Authorization") String authToken);

    @POST("cus-orders")
    Call<ApiResponse<Integer>> createOrder(@Body CustomerOrderRequestModel orderRequest);

    //Get profile
    @GET("users/profile")
    Call<ProfileResponseModel> getProfile(@Query("id") String userId);

    @Multipart
    @PUT("users/profile")
    Call<ApiResponse> updateProfile(
            @Part("id") RequestBody id,
            @Part("name") RequestBody name,
            @Part("date_of_birth") RequestBody dateOfBirth,
            @Part("address") RequestBody address,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part profilePic
    );

    @FormUrlEncoded
    @POST("users/login")
    Call<TokenResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

  
    //get  orderhis
    @GET("cus-orders/history/{userId}")
    Call<List<OrderHistoryResponseModel>> getOrderHistory(@Path("userId") String userId);
  
    @FormUrlEncoded
    @POST("users/register")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("address") String address
    );

}