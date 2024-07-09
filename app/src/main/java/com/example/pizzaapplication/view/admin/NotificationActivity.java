package com.example.pizzaapplication.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.NotiAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Notification;
import com.example.pizzaapplication.data.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> content, date, status;
    ArrayList<Integer> orderId;
    NotiAdapter notiAdapter;
    NotificationRepository notificationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.notiRecyclerView);
        content = new ArrayList<>();
        date = new ArrayList<>();
        status = new ArrayList<>();
        orderId = new ArrayList<>();
        notificationRepository = new NotificationRepository(RetrofitClient.getApiService());
        notificationRepository.getNoti(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    List<Notification> notifications = response.body();
                    if (notifications != null) {
                        for (Notification notification : notifications) {
                            content.add(notification.getContent());
                            date.add(notification.getReceivedAt());
                            status.add(String.valueOf(notification.getStatus()));
                            orderId.add(notification.getId());
                        }
                        notiAdapter = new NotiAdapter(NotificationActivity.this, NotificationActivity.this, content, date, status, orderId);
                        recyclerView.setAdapter(notiAdapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                // Handle failure
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