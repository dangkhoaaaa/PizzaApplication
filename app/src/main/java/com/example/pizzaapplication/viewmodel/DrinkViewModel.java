package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;
import com.example.pizzaapplication.data.repository.DrinkRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkViewModel extends ViewModel {

    private MutableLiveData<DrinkResponseModel> drinks;
    private DrinkRepository drinkRepository;

    public DrinkViewModel(DrinkRepository drinkRepository) {
        drinks = new MutableLiveData<>();
        this.drinkRepository = drinkRepository;
        fetchDrinks();
    }

    public LiveData<DrinkResponseModel> getDrinks() {
        return drinks;
    }

    private void fetchDrinks() {
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
        });
    }
}
