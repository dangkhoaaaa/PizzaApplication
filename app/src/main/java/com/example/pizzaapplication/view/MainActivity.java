package com.example.pizzaapplication.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pizzaapplication.R;

import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.utils.Utils;
import com.example.pizzaapplication.view.PizzaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataLocalManager.init(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //check token
                //check token
                if (DataLocalManager.getInstance().getToken() != null) {
                    Toast.makeText(MainActivity.this, "TOKEN: " + DataLocalManager.getInstance().getToken(), Toast.LENGTH_SHORT).show();
                }
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_pizza) {
                    loadFragment(new PizzaFragment());
                    return true;
                } else if (itemId == R.id.navigation_drink) {
                    loadFragment(new DrinkFragment());
                    return true;
                } else if (itemId == R.id.navigation_map) {
                    loadFragment(new MapFragment());
                    return true;
                } else if (itemId == R.id.navigation_account) {
                    if (Utils.isLoggedIn()) {
                        loadFragment(new ProfileFragment());
                        return true;
                    } else {
                        // Show login prompt if token is missing or empty
                        showLoginPrompt();
                    }
                    return false;
                }
                return false;
            }

        });

        // Mặc định hiển thị trang Pizza khi activity khởi động
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_pizza);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Only handle message and cart actions in onOptionsItemSelected
        if (id == R.id.action_notification || id == R.id.action_cart) {
            if (Utils.isLoggedIn()) {
                // User logged in, proceed with normal action
                if (id == R.id.action_notification) {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else if (id == R.id.action_cart) {
                    if (Utils.isCartEmpty()) {
                        Utils.showToast(this, "Your cart is empty. Please add items to the cart first.");
                    } else {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                    }
                }
            } else {
                // User not logged in, prompt for login
                showLoginPrompt();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showLoginPrompt() {
        Utils.showLoginPrompt(this, "You need to log in to access this section. Do you want to log in now?");
    }

}
