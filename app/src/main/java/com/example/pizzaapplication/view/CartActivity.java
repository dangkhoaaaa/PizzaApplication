package com.example.pizzaapplication.view;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Request.CustomerDrinkRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listViewCart);

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

            com.example.pizzaapplication.view.CartAdapter adapter = new com.example.pizzaapplication.view.CartAdapter(this, cartItems);
            listViewCart.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
