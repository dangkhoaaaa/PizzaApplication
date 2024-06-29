package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.DrinkAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.CustomerDrinkRequestModel;
import com.example.pizzaapplication.data.model.Response.DrinkModel;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;
import com.example.pizzaapplication.data.repository.DrinkRepository;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.viewmodel.DrinkViewModel;

import java.util.ArrayList;
import java.util.List;

public class DrinkFragment extends Fragment {

    private RecyclerView recyclerView;
    private DrinkAdapter drinkAdapter;
    private DrinkViewModel drinkViewModel;
    private static final String TAG = "DrinkFragment";
    private static List<CustomerDrinkRequestModel> cart = new ArrayList<>();

    public static List<CustomerDrinkRequestModel> getDrinkCart() {
        return cart;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_drink_fragment, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewDrink);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        drinkAdapter = new DrinkAdapter();
        recyclerView.setAdapter(drinkAdapter);
        String token = DataLocalManager.getInstance().getToken();
        if (token == null) {
            // Redirect to login activity if no token is found

        }

        // Initialize DrinkRepository and DrinkViewModel
        DrinkRepository drinkRepository = new DrinkRepository(RetrofitClient.getApiService());
        drinkViewModel = new DrinkViewModel(drinkRepository,token);

        // Observe LiveData from DrinkViewModel
        LiveData<DrinkResponseModel> liveDataDrinks = drinkViewModel.getDrinks();
        liveDataDrinks.observe(getViewLifecycleOwner(), new Observer<DrinkResponseModel>() {
            @Override
            public void onChanged(DrinkResponseModel drinkResponseModel) {
                if (drinkResponseModel != null) {
                    displayDrinks(drinkResponseModel.getDrinks());
                    logDrinks(drinkResponseModel.getDrinks());
                } else {
                    Log.d(TAG, "No drinks received");
                }
            }
        });

        // Set item click listener
        drinkAdapter.setOnItemClickListener(new DrinkAdapter.OnItemClickListener() {
            @Override
            public void onItemAddToCart(DrinkModel drink, int quantity) {
                addToCart(drink, quantity);
            }
        });

        return view;
    }

    private void displayDrinks(List<DrinkModel> drinks) {
        drinkAdapter.setDrinks(drinks);
    }

    private void addToCart(DrinkModel drink, int quantity) {
        // Add the drink to the cart with specified quantity
        cart.add(new CustomerDrinkRequestModel(quantity, drink.getId(), drink.getPrice() * quantity));
        // Show a toast message
        Toast.makeText(getContext(), quantity + " x " + drink.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }

    private void logDrinks(List<DrinkModel> drinks) {
        for (DrinkModel drink : drinks) {
            Log.d(TAG, "Drink: " + drink.getName() + ", Price: " + drink.getPrice());
        }
    }
}
