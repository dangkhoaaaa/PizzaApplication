package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pizzaapplication.R;

import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.view.PizzaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //check token
                if(DataLocalManager.getToken() != null) {
                    Toast.makeText(MainActivity.this, "TOKEN: " + DataLocalManager.getToken(), Toast.LENGTH_SHORT).show();
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
                    loadFragment(new ProfileFragment());
                    return true;
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
        if (id == R.id.action_notification) {
            // Xử lý khi click vào chuông thông báo
            return true;
        } else if (id == R.id.action_cart) {
            // Xử lý khi click vào biểu tượng giỏ hàng
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);

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
}
