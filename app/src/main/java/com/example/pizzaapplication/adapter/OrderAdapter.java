package com.example.pizzaapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Order;
import com.example.pizzaapplication.data.model.Response.OrderHistoryResponseModel;
import com.example.pizzaapplication.viewmodel.OrderHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderHistoryResponseModel> orderList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(OrderHistoryResponseModel order);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOrders(List<OrderHistoryResponseModel> orders) {
        this.orderList = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderHistoryResponseModel order = orderList.get(position);
        holder.bind(order, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView orderIdTextView, totalAmountTextView, orderDateTextView;
        private ImageView imgOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            totalAmountTextView = itemView.findViewById(R.id.totalAmountTextView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            imgOrder = itemView.findViewById(R.id.imgOrder);
        }

        public void bind(final OrderHistoryResponseModel order, final OnItemClickListener listener) {
            orderIdTextView.setText("Order ID: " + order.getOrderId());
            totalAmountTextView.setText("Total Amount: " + order.getTotalAmount() + " VND");
            orderDateTextView.setText("Date order: " + order.getOrderDate());

            // Load image using Glide
            Glide.with(itemView.getContext())
                    .load(order.getImage())
                    .into(imgOrder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(order);
                }
            });
        }
    }
}
