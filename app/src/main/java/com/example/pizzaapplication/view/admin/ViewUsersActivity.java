package com.example.pizzaapplication.view.admin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.UserAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.UsersModel;
import com.example.pizzaapplication.data.model.Response.UsersResponseModel;
import com.example.pizzaapplication.data.repository.UserRepository;
import com.example.pizzaapplication.viewmodel.UserViewModel;

import java.util.List;

public class ViewUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private UserViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);

        UserRepository userRepository = new UserRepository(RetrofitClient.getApiService());
        usersViewModel = new UserViewModel(userRepository);
        // Observe LiveData
        LiveData<UsersResponseModel> liveData = usersViewModel.getUsers();
        liveData.observe(this, usersResponseModel -> {
            if (usersResponseModel != null) {
                displayUsers(usersResponseModel.getUsers());
                logUsers(usersResponseModel.getUsers());
            }
        });

    }

    private void displayUsers(List<UsersModel> users) {
        userAdapter.setUsers(users);
    }

    private void logUsers(List<UsersModel> users) {
        for (UsersModel user : users) {
            Log.d("ViewUsersActivity", user.getName());
        }
    }
}
