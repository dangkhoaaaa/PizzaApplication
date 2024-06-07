package com.example.pizzaapplication.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.UserAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.User;
import com.example.pizzaapplication.data.repository.MainRepository;
import com.example.pizzaapplication.viewmodel.MainViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);

        // Initialize MainRepository and MainViewModel
        MainRepository mainRepository = new MainRepository(RetrofitClient.getApiService());
        mainViewModel = new MainViewModel(mainRepository);

        // Observe LiveData from MainViewModel
        LiveData<List<User>> liveDataUsers = mainViewModel.getUsers();
        liveDataUsers.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // Update UI
                displayUsers(users);
            }
        });
    }

    private void displayUsers(List<User> users) {
        userAdapter.setUsers(users);
    }
}
