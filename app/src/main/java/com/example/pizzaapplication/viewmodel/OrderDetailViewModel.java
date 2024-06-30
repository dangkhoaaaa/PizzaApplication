package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Response.OrderDetailResponseModel;
import com.example.pizzaapplication.data.repository.OrderDetailRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailViewModel extends ViewModel {
    private MutableLiveData<OrderDetailResponseModel> orderDetail;
    private OrderDetailRepository orderDetailRepository;
    private String orderId;

    public OrderDetailViewModel(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
        orderDetail = new MutableLiveData<>();
    }

    public LiveData<OrderDetailResponseModel> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
        fetchOrderDetail(orderId);
    }

    private void fetchOrderDetail(String orderId) {
        orderDetailRepository.getOrderDetails(orderId, new Callback<OrderDetailResponseModel>() {
            @Override
            public void onResponse(Call<OrderDetailResponseModel> call, Response<OrderDetailResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderDetail.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponseModel> call, Throwable t) {
                // Handle failure
            }
        });
    }
}