package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.DrinkAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Drink;
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
    private static List<Drink> displayCart = new ArrayList<>();
    private EditText  minPriceEditText, maxPriceEditText;
    private ImageView searchButton;
    private SearchView searchEditText;
    private LinearLayout priceFilterLayout;
    public static List<CustomerDrinkRequestModel> getDrinkCart() {
        return cart;
    }

    public static List<Drink> getDrinkDisplayCart() {
        return displayCart;
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

        // Initialize Search and Filter Inputs
        searchEditText = view.findViewById(R.id.searchEditTextDrink);
        minPriceEditText = view.findViewById(R.id.minPriceEditTextDrink);
        maxPriceEditText = view.findViewById(R.id.maxPriceEditTextDrink);
        priceFilterLayout = view.findViewById(R.id.priceFilterLayout);
//        searchButton = view.findViewById(R.id.searchButtonDrink);

        String token = DataLocalManager.getInstance().getToken();
        if (token == null) {
            // Redirect to login activity if no token is found
        }

        // Initialize DrinkRepository and DrinkViewModel
        DrinkRepository drinkRepository = new DrinkRepository(RetrofitClient.getApiService());
        drinkViewModel = new DrinkViewModel(drinkRepository, token);

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

        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onChangeSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onChangeSearch(newText);
                return false;
            }
        });

        // Show price filters when the search view is clicked
        searchEditText.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceFilterLayout.setVisibility(View.VISIBLE);
            }
        });

        // Hide price filters when the search view is closed
        searchEditText.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                priceFilterLayout.setVisibility(View.GONE);
                return false;
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
        boolean isExist = false;
        // Check if the drink already exists in cart
        for (CustomerDrinkRequestModel item : cart) {
            if (item.getDrinkId() == drink.getId()) {
                // If exists, increase the quantity
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(item.getPrice() + drink.getPrice() * quantity);
                // Update display cart with the increased quantity
                for (Drink display : displayCart) {
                    if (display.getName().equals(drink.getName())) {
                        display.setQuantity(display.getQuantity() + quantity);
                        display.setPrice(display.getPrice() + drink.getPrice() * quantity);
                        break;
                    }
                }

                isExist = true;
                break;
            }
        }

        // If not exists, add the drink to cart with specified quantity
        if (!isExist) {
            cart.add(new CustomerDrinkRequestModel(quantity, drink.getId(), drink.getPrice() * quantity));
            displayCart.add(new Drink(drink.getName(), drink.getImage(), quantity, drink.getPrice() * quantity));
        }

        // Show a toast message
        Toast.makeText(getContext(), quantity + " x " + drink.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }

    private void logDrinks(List<DrinkModel> drinks) {
        for (DrinkModel drink : drinks) {
            Log.d(TAG, "Drink: " + drink.getName() + ", Price: " + drink.getPrice());
        }
    }

    public static void Clear() {
        displayCart.clear();
        cart.clear();
    }

    private  void onChangeSearch(String search){
        int minPrice = minPriceEditText.getText().toString().isEmpty() ? 0 : Integer.parseInt(minPriceEditText.getText().toString());
        int maxPrice = maxPriceEditText.getText().toString().isEmpty() ? 1000000 : Integer.parseInt(maxPriceEditText.getText().toString());

        // Fetch filtered pizzas
        int currentPage = 1;
        int pageSize = 4;
        boolean sortByPrice = true;
        boolean descending = true;

        if (search.isEmpty()) {
            // Fetch all pizzas
            drinkViewModel.fetchDrinks(currentPage, pageSize, minPrice, maxPrice, null, sortByPrice, descending);
        } else {
            // Fetch pizzas with the search query
            drinkViewModel.fetchDrinks(currentPage, pageSize, minPrice, maxPrice, search.trim(), sortByPrice, descending);
        }
    }
}
