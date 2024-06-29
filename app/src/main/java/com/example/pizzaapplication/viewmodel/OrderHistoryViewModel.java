package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Response.OrderHistoryResponseModel;
import com.example.pizzaapplication.data.repository.OrderHistoryRepository;
import com.example.pizzaapplication.share.DataLocalManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryViewModel extends ViewModel {
    private MutableLiveData<List<OrderHistoryResponseModel>> orderHistory;
    private OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryViewModel(OrderHistoryRepository orderHistoryRepository) {
        orderHistory = new MutableLiveData<>();
        this.orderHistoryRepository = orderHistoryRepository;
        fetchOrderHistory();
    }

    public LiveData<List<OrderHistoryResponseModel>> getOrderHistory() {
        return orderHistory;
    }

    private void fetchOrderHistory() {
        // Here's the correction based on the API definition in OrderHistoryRepository:
        orderHistoryRepository.getOrderHistorys(new Callback<List<OrderHistoryResponseModel>>() {
            @Override
            public void onResponse(Call<List<OrderHistoryResponseModel>> call, Response<List<OrderHistoryResponseModel>> response) {
                if (response.isSuccessful()) {
                    orderHistory.setValue(response.body());
                } else {
                    // Handle API error (e.g., logging, displaying an error message)
                    // You can use response.code() or response.errorBody() to get more details
                }
            }

            @Override
            public void onFailure(Call<List<OrderHistoryResponseModel>> call, Throwable t) {
                // Handle network error (e.g., logging, displaying an error message)
            }
        });
    }
}
