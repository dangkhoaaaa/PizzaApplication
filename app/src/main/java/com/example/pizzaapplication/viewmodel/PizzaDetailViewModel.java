package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Response.SizeModel;
import com.example.pizzaapplication.data.model.Response.SizeResponseModel;
import com.example.pizzaapplication.data.model.Response.ToppingModel;
import com.example.pizzaapplication.data.model.Response.ToppingResponseModel;
import com.example.pizzaapplication.data.repository.SizeRepository;
import com.example.pizzaapplication.data.repository.ToppingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzaDetailViewModel extends ViewModel {

    private MutableLiveData<ToppingResponseModel> toppings;
    private MutableLiveData<SizeResponseModel> sizes;
    private ToppingRepository toppingRepository;
    private SizeRepository sizeRepository;

    public PizzaDetailViewModel(ToppingRepository toppingRepository, SizeRepository sizeRepository) {
        this.toppingRepository = toppingRepository;
        this.sizeRepository = sizeRepository;
        toppings = new MutableLiveData<>();
        sizes = new MutableLiveData<>();
        fetchToppings();
        fetchSizes();
    }

    public LiveData<ToppingResponseModel> getToppings() {
        return toppings;
    }

    public LiveData<SizeResponseModel> getSizes() {
        return sizes;
    }

    private void fetchToppings() {
        toppingRepository.getToppings(new Callback<ToppingResponseModel>() {
            @Override
            public void onResponse(Call<ToppingResponseModel> call, Response<ToppingResponseModel> response) {
                if (response.isSuccessful()) {
                    toppings.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ToppingResponseModel> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void fetchSizes() {
        sizeRepository.getSizes(new Callback<SizeResponseModel>() {
            @Override
            public void onResponse(Call<SizeResponseModel> call, Response<SizeResponseModel> response) {
                if (response.isSuccessful()) {
                    sizes.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SizeResponseModel> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
