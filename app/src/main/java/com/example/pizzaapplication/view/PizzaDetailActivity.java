package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.SizeAdapter;
import com.example.pizzaapplication.adapter.ToppingAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;
import com.example.pizzaapplication.data.model.Response.PizzaModel;
import com.example.pizzaapplication.data.model.Response.SizeModel;
import com.example.pizzaapplication.data.model.Response.SizeResponseModel;
import com.example.pizzaapplication.data.model.Response.ToppingModel;
import com.example.pizzaapplication.data.model.Response.ToppingResponseModel;
import com.example.pizzaapplication.data.repository.SizeRepository;
import com.example.pizzaapplication.data.repository.ToppingRepository;
import com.example.pizzaapplication.viewmodel.PizzaDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class PizzaDetailActivity extends AppCompatActivity {

    private PizzaDetailViewModel pizzaDetailViewModel;
    private Spinner spinnerToppings, spinnerSizes;
    private TextView textViewPrice;
    private PizzaModel pizza;
    private double toppingPrice = 0;
    private double sizePrice = 0;
    private Button buttonAddToCart;

    // A static list to store cart items
    private static List<CustomerPizzaRequestModel> cart = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        TextView textViewName = findViewById(R.id.textViewPizzaName);
        TextView textViewDescription = findViewById(R.id.textViewPizzaDescription);
        textViewPrice = findViewById(R.id.textViewPizzaPrice);
        spinnerToppings = findViewById(R.id.spinnerToppings);
        spinnerSizes = findViewById(R.id.spinnerSizes);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);

        // Get the pizza data from the intent
        if (getIntent() != null && getIntent().getExtras() != null) {
            pizza = (PizzaModel) getIntent().getSerializableExtra("pizza");
            if (pizza != null) {
                textViewName.setText(pizza.getName());
                textViewDescription.setText(pizza.getDescription());
                textViewPrice.setText(String.valueOf(pizza.getPrice()));
            }
        }

        // Initialize repositories and ViewModel
        ToppingRepository toppingRepository = new ToppingRepository(RetrofitClient.getApiService());
        SizeRepository sizeRepository = new SizeRepository(RetrofitClient.getApiService());
        pizzaDetailViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PizzaDetailViewModel(toppingRepository, sizeRepository);
            }
        }).get(PizzaDetailViewModel.class);

        // Observe toppings LiveData
        pizzaDetailViewModel.getToppings().observe(this, new Observer<ToppingResponseModel>() {
            @Override
            public void onChanged(ToppingResponseModel toppingModels) {
                if (toppingModels != null) {
                    List<ToppingModel> list = toppingModels.getToppingModels();
                    ToppingAdapter adapter = new ToppingAdapter(PizzaDetailActivity.this, list);
                    spinnerToppings.setAdapter(adapter);
                }
            }
        });

        // Observe sizes LiveData
        pizzaDetailViewModel.getSizes().observe(this, new Observer<SizeResponseModel>() {
            @Override
            public void onChanged(SizeResponseModel sizeModels) {
                if (sizeModels != null) {
                    List<SizeModel> list = sizeModels.getSizeModels();
                    SizeAdapter adapter = new SizeAdapter(PizzaDetailActivity.this, list);
                    spinnerSizes.setAdapter(adapter);
                }
            }
        });

        // Set listeners for spinners
        spinnerToppings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToppingModel selectedTopping = (ToppingModel) parent.getSelectedItem();
                toppingPrice = (selectedTopping.getName().equals("No Topping")) ? 0 : selectedTopping.getPrice();
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toppingPrice = 0;
                updatePrice();
            }
        });

        spinnerSizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SizeModel selectedSize = (SizeModel) parent.getSelectedItem();
                sizePrice = selectedSize.getPrice();
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sizePrice = 0;
                updatePrice();
            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void updatePrice() {
        double totalPrice = pizza.getPrice() + toppingPrice + sizePrice;
        textViewPrice.setText(String.valueOf(totalPrice));
    }

    private void addToCart() {
        ToppingModel selectedTopping = (ToppingModel) spinnerToppings.getSelectedItem();
        SizeModel selectedSize = (SizeModel) spinnerSizes.getSelectedItem();

        CustomerPizzaRequestModel customerPizza = new CustomerPizzaRequestModel();
        customerPizza.setPizzaId(pizza.getPizzaId());
        customerPizza.setSizeId(selectedSize.getSizeId());
        customerPizza.setId(1);
        customerPizza.setToppingId(selectedTopping.getToppingId());
        customerPizza.setPrice( pizza.getPrice() + toppingPrice + sizePrice);
        cart.add(customerPizza);

        Toast.makeText(this, "Pizza added to cart!", Toast.LENGTH_SHORT).show();
    }

    public static List<CustomerPizzaRequestModel> getCart() {
        return cart;
    }

}