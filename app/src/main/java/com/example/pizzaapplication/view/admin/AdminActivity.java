package com.example.pizzaapplication.view.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pizzaapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Notification;
import com.example.pizzaapplication.data.repository.NotificationRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    TextView textView;
    ImageButton image_notification;
    NotificationRepository notificationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        textView = findViewById(R.id.textNoti);
        image_notification = findViewById(R.id.imageButton);
        notificationRepository = new NotificationRepository(RetrofitClient.getApiService());
        notificationRepository.getNoti(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    List<Notification> notifications = response.body();
                    if (notifications != null) {
                        int count = 0;
                        for (Notification notification : notifications) {
                            if (notification.getStatus() == 1) {
                                count++;
                            }
                        }
                        if (count > 0) {
                            textView.setText("" + count );
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.GONE);
                        }
                    } else {
                        textView.setVisibility(View.GONE);
                    }
                } else {
                    textView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                textView.setText("Failed to get notifications");
            }
        });

        image_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AdminActivity.this, NotificationActivity.class),1);
            }
        });

        findViewById(R.id.buttonEditPizza).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminPizza.class));
            }
        });

        findViewById(R.id.buttonViewDrinks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminDrinkActivity.class));
            }
        });

        findViewById(R.id.buttonViewUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ViewUsersActivity.class));
            }
        });

        findViewById(R.id.buttonViewDashboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, DashboardActivity.class));
            }
        });

        findViewById(R.id.buttonChatWithCustomers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminChatUsersActivity.class));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

}
