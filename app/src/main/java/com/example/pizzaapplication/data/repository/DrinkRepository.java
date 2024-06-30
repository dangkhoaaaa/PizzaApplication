package com.example.pizzaapplication.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Request.DrinkCreateRequestModel;
import com.example.pizzaapplication.data.model.Request.DrinkUpdateRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkRepository {

    private ApiService apiService;

    public DrinkRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getDrinks(Callback<DrinkResponseModel> callback, String authToken) {
        Call<DrinkResponseModel> call = apiService.getDrinks(authToken);
        call.enqueue(callback);
    }

    public LiveData<ApiResponse> createDrink(DrinkCreateRequestModel drink) {
        MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
        double priceValue = drink.getPrice();
        String priceString = String.valueOf(priceValue);
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), priceString);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), drink.getName());
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), drink.getDescription());
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), drink.getImage());

        apiService.createDrink(name, description, price, image).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    responseLiveData.setValue(response.body());
                } else {
                    // responseLiveData.setValue(new ApiResponse("Failed to create drink"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // responseLiveData.setValue(new ApiResponse("Network error: " + t.getMessage()));
            }
        });
        return responseLiveData;
    }

    public LiveData<ApiResponse> updateDrink(DrinkUpdateRequestModel drink) {
        MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
        double priceValue = drink.getPrice();
        String priceString = String.valueOf(priceValue);
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), priceString);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(drink.getId()));
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), drink.getName());
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), drink.getDescription());
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), drink.getImage());

        apiService.updateDrink(id, name, description, price, image).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    responseLiveData.setValue(response.body());
                } else {
                    // responseLiveData.setValue(new ApiResponse("Failed to update drink"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // responseLiveData.setValue(new ApiResponse("Network error: " + t.getMessage()));
            }
        });
        return responseLiveData;
    }

}
