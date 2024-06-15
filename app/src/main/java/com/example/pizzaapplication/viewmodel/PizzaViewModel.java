package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.repository.MainRepository;
import com.example.pizzaapplication.data.repository.PizzaRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzaViewModel extends ViewModel {

    private MutableLiveData<PizzaResponseModel> pizzas;
    private PizzaRepository pizzaRepository;

    public PizzaViewModel(PizzaRepository pizzaRepository) {
        pizzas = new MutableLiveData<>();
        this.pizzaRepository = pizzaRepository;
        fetchPizzas();
    }

    public LiveData<PizzaResponseModel> getPizzas() {
        return pizzas;
    }

    private void fetchPizzas() {
        pizzaRepository.getPizzas(new Callback<PizzaResponseModel>() {
            @Override
            public void onResponse(Call<PizzaResponseModel> call, Response<PizzaResponseModel> response) {
                if (response.isSuccessful()) {
                    pizzas.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PizzaResponseModel> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
