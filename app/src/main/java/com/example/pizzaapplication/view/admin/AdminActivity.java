package com.example.pizzaapplication.view.admin;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Notification;
import com.example.pizzaapplication.data.repository.NotificationRepository;
import com.example.pizzaapplication.utils.NotificationUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Date;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    TextView textView;
    ImageButton image_notification;
    NotificationRepository notificationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Create the notification channel
        NotificationUtils.createNotificationChannel(this);

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
                            sendNotification(count); // Gọi thông báo khi có đơn hàng mới
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

    private void sendNotification(int number) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pizza_logo);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationUtils.CHANNEL_ID)
                .setContentTitle("New order")
                .setContentText("There are " + number + " new orders")
                .setSmallIcon(R.drawable.baseline_notifications_none_24)
                .setLargeIcon(bitmap)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(getNotificationId(), builder.build());
        }
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}
