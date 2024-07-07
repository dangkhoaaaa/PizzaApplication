package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Request.DrinkCreateRequestModel;
import com.example.pizzaapplication.data.model.Request.DrinkUpdateRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.repository.DrinkRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkViewModel extends ViewModel {

    private MutableLiveData<DrinkResponseModel> drinks;
    private DrinkRepository drinkRepository;

    public DrinkViewModel(DrinkRepository drinkRepository,String au) {
        drinks = new MutableLiveData<>();
        this.drinkRepository = drinkRepository;
        fetchDrinks( au);
        fetchDrinks(1, 4, 0, 10000000, "", true, true);

    }

    public LiveData<DrinkResponseModel> getDrinks() {
        return drinks;
    }
    public void fetchDrinks(int currentPage, int pageSize, int minPrice, int maxPrice, String name, boolean sortByPrice, boolean descending) {
        drinkRepository.getDrinks(currentPage, pageSize, minPrice, maxPrice, name, sortByPrice, descending, new Callback<DrinkResponseModel>() {
            @Override
            public void onResponse(Call<DrinkResponseModel> call, Response<DrinkResponseModel> response) {
                if (response.isSuccessful()) {
                    drinks.setValue(response.body());
                } else {
                    drinks.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<DrinkResponseModel> call, Throwable t) {
                drinks.setValue(null);
            }
        });
    }
    private void fetchDrinks(String authToken1) {
        String authToken = "Bearer "+authToken1; // Replace with actual token
        drinkRepository.getDrinks(new Callback<DrinkResponseModel>() {
            @Override
            public void onResponse(Call<DrinkResponseModel> call, Response<DrinkResponseModel> response) {
                if (response.isSuccessful()) {
                    drinks.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DrinkResponseModel> call, Throwable t) {
                // Handle failure
            }
        }, authToken);
    }

    public LiveData<ApiResponse> createDrink(DrinkCreateRequestModel drink) {
        return drinkRepository.createDrink(drink);
    }

    public LiveData<ApiResponse> updateDrink(DrinkUpdateRequestModel drink) {
        return drinkRepository.updateDrink(drink);
    }

}
