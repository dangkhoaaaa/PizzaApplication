package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Request.PizzaCreateRequestModel;
import com.example.pizzaapplication.data.model.Request.PizzaUpdateRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
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
        fetchPizzas(1, 4, 0, 10000000, "", true, true);
    }

    public LiveData<PizzaResponseModel> getPizzas() {
        return pizzas;
    }
    public void fetchPizzas(int currentPage, int pageSize, int minPrice, int maxPrice, String name, boolean sortByPrice, boolean descending) {
        pizzaRepository.getPizzas(currentPage, pageSize, minPrice, maxPrice, name, sortByPrice, descending, new Callback<PizzaResponseModel>() {
            @Override
            public void onResponse(Call<PizzaResponseModel> call, Response<PizzaResponseModel> response) {
                if (response.isSuccessful()) {
                    pizzas.setValue(response.body());
                } else {
                    pizzas.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PizzaResponseModel> call, Throwable t) {
                pizzas.setValue(null);
            }
        });
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

    public LiveData<ApiResponse> createPizza(PizzaCreateRequestModel pizza) {
        return pizzaRepository.createPizza(pizza);
    }

    public LiveData<ApiResponse> updatePizza(PizzaUpdateRequestModel pizza) {
        return pizzaRepository.updatePizza(pizza);
    }

}
