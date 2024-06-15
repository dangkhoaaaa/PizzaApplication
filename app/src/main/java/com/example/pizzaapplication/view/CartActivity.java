package com.example.pizzaapplication.view;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listViewCart);

        List<CustomerPizzaRequestModel> cart = PizzaDetailActivity.getCart();
        if (cart != null && !cart.isEmpty()) {
            com.example.pizzaapplication.view.CartAdapter adapter = new com.example.pizzaapplication.view.CartAdapter(this, cart);
            listViewCart.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
