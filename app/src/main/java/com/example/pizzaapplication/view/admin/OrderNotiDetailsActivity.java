package com.example.pizzaapplication.view.admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pizzaapplication.R;

public class OrderNotiDetailsActivity extends AppCompatActivity {


    TextView name_txt, address_txt, phone_txt, order_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_noti_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name_txt = findViewById(R.id.name_txt);
        address_txt = findViewById(R.id.address_txt);
        phone_txt = findViewById(R.id.phone_txt);
        order_txt = findViewById(R.id.order_txt);

        name_txt.setText(getIntent().getStringExtra("name"));
        address_txt.setText(getIntent().getStringExtra("address"));
        phone_txt.setText(getIntent().getStringExtra("phone"));
        order_txt.setText(getIntent().getStringExtra("order"));

    }
}