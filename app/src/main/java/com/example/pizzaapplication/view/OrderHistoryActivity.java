package com.example.pizzaapplication.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.OrderAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.OrderHistoryResponseModel;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.repository.OrderHistoryRepository;
import com.example.pizzaapplication.data.repository.PizzaRepository;
import com.example.pizzaapplication.viewmodel.OrderHistoryViewModel;
import com.example.pizzaapplication.viewmodel.PizzaViewModel;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderHistoryViewModel orderHistoryViewModel;
    private static final String TAG = "OrderHistoryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        // Initialize RecyclerView
        recyclerView = findViewById(R.id.rcOrderHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter();
        recyclerView.setAdapter(orderAdapter);

//        // Initialize OderHistoryRepository and OderHistoryViewModel
//        OrderHistoryRepository oderHistoryRepository = new OrderHistoryRepository(RetrofitClient.getApiService());
//        orderHistoryViewModel = new OrderHistoryViewModel(oderHistoryRepository);

        // Initialize OrderHistoryRepository and OrderHistoryViewModel
        OrderHistoryRepository orderHistoryRepository = new OrderHistoryRepository(RetrofitClient.getApiService());
        orderHistoryViewModel = new OrderHistoryViewModel(orderHistoryRepository);

        // Observe LiveData from OrderHistoryViewModel
        LiveData<List<OrderHistoryResponseModel>> liveDataOrderHistories = orderHistoryViewModel.getOrderHistory();
        liveDataOrderHistories.observe(this, new Observer<List<OrderHistoryResponseModel>>() {
            @Override
            public void onChanged(List<OrderHistoryResponseModel> orderHistoryResponseModels) {
                if (orderHistoryResponseModels != null) {
                    displayOrders(orderHistoryResponseModels);
                    logOrders(orderHistoryResponseModels);
                } else {
                    Log.d(TAG, "No order histories received");
                }
            }
        });
    }

    private void displayOrders(List<OrderHistoryResponseModel> orders) {
        orderAdapter.setOrders(orders);
    }

    private void logOrders(List<OrderHistoryResponseModel> orders) {
        for (OrderHistoryResponseModel order : orders) {
            Log.d(TAG, "Order ID: " + order.getOrderId() + ", Total Amount: " + order.getTotalAmount());
        }
    }
}