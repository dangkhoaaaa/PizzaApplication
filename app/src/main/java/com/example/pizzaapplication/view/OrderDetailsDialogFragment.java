package com.example.pizzaapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.BillDetailModel;
import com.example.pizzaapplication.data.model.Response.OrderDetailResponseModel;
import com.example.pizzaapplication.data.repository.OrderDetailRepository;
import com.example.pizzaapplication.data.repository.PizzaRepository;
import com.example.pizzaapplication.utils.Utils;
import com.example.pizzaapplication.viewmodel.OrderDetailViewModel;

public class OrderDetailsDialogFragment extends DialogFragment {

    private OrderDetailViewModel orderDetailViewModel;
    private TextView orderDetailsTextView;

    public static OrderDetailsDialogFragment newInstance(String orderId) {
        OrderDetailsDialogFragment fragment = new OrderDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString("orderId", orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderDetailsTextView = view.findViewById(R.id.orderDetailsTextView);

        String orderId = getArguments().getString("orderId");

        // Initialize orderDetailViewModel
        OrderDetailRepository orderDetailRepository = new OrderDetailRepository(RetrofitClient.getApiService());
        orderDetailViewModel = new OrderDetailViewModel(orderDetailRepository);
        orderDetailViewModel.setOrderId(orderId);
        orderDetailViewModel.getOrderDetail().observe(getViewLifecycleOwner(), this::displayOrderDetails);
    }

    private void displayOrderDetails(OrderDetailResponseModel orderDetail) {
        if (orderDetail == null) return;

        StringBuilder details = new StringBuilder();
        details.append("Date: ").append(Utils.formatDateOfBirth(orderDetail.getDateTime())).append("\n\n");

        for (BillDetailModel item : orderDetail.getBillDetails()) {
            details.append(item.getName())
                    .append("\n")
                    .append("Quantity: ")
                    .append(item.getQuantity())
                    .append("\n")
                    .append("Price: ")
                    .append(item.getPrice())
                    .append("\n\n");
        }

        details.append("Total Price: ").append(orderDetail.getTotalPrice()).append(" VND");
        orderDetailsTextView.setText(details.toString());
    }
}
