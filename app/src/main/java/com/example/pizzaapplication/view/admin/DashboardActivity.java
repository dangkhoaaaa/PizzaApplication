package com.example.pizzaapplication.view.admin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.DashboardResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    TextView totalUser, totalOrder, totalRevenue, pizzaName, pizzaPrice, drinkName, drinkPrice;
    ImageView pizzaImage, drinkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        totalUser = findViewById(R.id.totalUsers);
        totalOrder = findViewById(R.id.totalOrders);
        totalRevenue = findViewById(R.id.totalRevenue);

        pizzaName = findViewById(R.id.pizzaName);
        pizzaPrice = findViewById(R.id.pizzaPrice);
        pizzaImage = findViewById(R.id.imageViewPizza);
        drinkName = findViewById(R.id.drinkName);
        drinkPrice = findViewById(R.id.drinkPrice);
        drinkImage = findViewById(R.id.imageViewDrink);

        ApiService apiService = RetrofitClient.getApiService();
        Call<DashboardResponse> call = apiService.getDashboard();

        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (response.isSuccessful()) {
                    DashboardResponse dashboard = response.body();

                    totalUser.setText(String.valueOf(dashboard.getTotalUsers()));
                    totalOrder.setText(String.valueOf(dashboard.getTotalOrders()));
                    totalRevenue.setText(String.valueOf(dashboard.getTotalRevenue()));

                    pizzaName.setText(dashboard.getTopPizzaOrder().getName());
                    pizzaPrice.setText(String.valueOf(dashboard.getTopPizzaOrder().getPrice()));
                    Picasso.get().load(dashboard.getTopPizzaOrder().getImage()).into(pizzaImage);

                    drinkName.setText(dashboard.getTopDrinkOrder().getName());
                    drinkPrice.setText(String.valueOf(dashboard.getTopDrinkOrder().getPrice()));
                    Picasso.get().load(dashboard.getTopDrinkOrder().getImage()).into(drinkImage);
                } else {
                    Toast.makeText(DashboardActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}