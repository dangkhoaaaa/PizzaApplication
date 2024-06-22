package com.example.pizzaapplication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.CartAdapter;
import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.CustomerDrinkRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerOrderRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;
    private Button buttonCheckout;
    private boolean isCheckoutInProgress = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listViewCart);
        buttonCheckout = findViewById(R.id.buttonCheckout);

        List<CustomerPizzaRequestModel> pizzaCart = PizzaDetailActivity.getCart();
        List<CustomerDrinkRequestModel> drinkCart = DrinkFragment.getDrinkCart();

        if ((pizzaCart != null && !pizzaCart.isEmpty()) || (drinkCart != null && !drinkCart.isEmpty())) {
            List<Object> cartItems = new ArrayList<>();
            if (pizzaCart != null) {
                cartItems.addAll(pizzaCart);
            }
            if (drinkCart != null) {
                cartItems.addAll(drinkCart);
            }

            CartAdapter adapter = new CartAdapter(this, cartItems);
            listViewCart.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        }

        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheckoutInProgress) {
                    isCheckoutInProgress = true;
                    processCheckout(pizzaCart, drinkCart);
                } else {
                    Toast.makeText(CartActivity.this, "Checkout is already in progress", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void processCheckout(List<CustomerPizzaRequestModel> pizzaCart, List<CustomerDrinkRequestModel> drinkCart) {
        // Construct the order request model
        CustomerOrderRequestModel orderRequest = new CustomerOrderRequestModel();
        orderRequest.setUserId(1); // Replace with actual user ID
        orderRequest.setCustomerPizzas(pizzaCart);
        orderRequest.setCustomerDrinks(drinkCart);

        // Make the API call
        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse<Integer>> call = apiService.createOrder(orderRequest);

        call.enqueue(new Callback<ApiResponse<Integer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                isCheckoutInProgress = false;
                if (response.isSuccessful() && response.body() != null && response.body().getIsSuccess()) {
                    int customerOrderId = response.body().getData();
                    Toast.makeText(CartActivity.this, "Order created successfully. Order ID: " + customerOrderId, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CartActivity.this, "Failed to create order", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Integer>> call, Throwable t) {
                isCheckoutInProgress = false;
                Toast.makeText(CartActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
