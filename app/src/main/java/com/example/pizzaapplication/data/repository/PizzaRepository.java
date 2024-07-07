package com.example.pizzaapplication.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Request.PizzaCreateRequestModel;
import com.example.pizzaapplication.data.model.Request.PizzaUpdateRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PizzaRepository {

    private ApiService apiService;

    public PizzaRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getPizzas(Callback<PizzaResponseModel> callback) {
        Call<PizzaResponseModel> call = apiService.getPizza();
        call.enqueue(callback);
    }
    public void getPizzas(int currentPage, int pageSize, int minPrice, int maxPrice, String name, boolean sortByPrice, boolean descending, Callback<PizzaResponseModel> callback) {
        Call<PizzaResponseModel> call = apiService.getPizzas(currentPage, pageSize, minPrice, maxPrice, name, sortByPrice, descending);
        call.enqueue(callback);
    }
    public LiveData<ApiResponse> createPizza(PizzaCreateRequestModel pizza) {
        MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
        double priceValue = pizza.getPrice();
        String priceString = String.valueOf(priceValue);
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), priceString);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getName());
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getDescription());
        //  RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getPrice());
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getImage());

        apiService.createPizza(name,description,price,image).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    responseLiveData.setValue(response.body());
                } else {
                 //   responseLiveData.setValue(new ApiResponse("Failed to create pizza"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
             //   responseLiveData.setValue(new ApiResponse("Network error: " + t.getMessage()));
            }
        });
        return responseLiveData;
    }

    public LiveData<ApiResponse> updatePizza(PizzaUpdateRequestModel pizza) {
        MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
        double priceValue = pizza.getPrice();
        String priceString = String.valueOf(priceValue);
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), priceString);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(pizza.getId()));
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getName());
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getDescription());
      //  RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getPrice());
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), pizza.getImage());

        apiService.updatePizza(id,name,description,price,image).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    responseLiveData.setValue(response.body());
                } else {
                  //  responseLiveData.setValue(new ApiResponse("Failed to update pizza"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
             //   responseLiveData.setValue(new ApiResponse("Network error: " + t.getMessage()));
            }
        });
        return responseLiveData;
    }
}
