package com.example.pizzaapplication.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.UsersModel;
import com.example.pizzaapplication.data.repository.UserRepository;
import com.example.pizzaapplication.viewmodel.UserViewModel;

import java.util.List;

public class AdminChatUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private UserViewModel userViewModel;
    private List<UsersModel> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat_users);

        listViewUsers = findViewById(R.id.listViewUsers);

        UserRepository userRepository = new UserRepository(RetrofitClient.getApiService());
        userViewModel = new UserViewModel(userRepository);

        // Observe LiveData
        userViewModel.getUsers().observe(this, usersResponseModel -> {
            if (usersResponseModel != null) {
                users = usersResponseModel.getUsers();
                displayUsers(users);
            }
        });

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UsersModel selectedUser = users.get(position);
                Intent intent = new Intent(AdminChatUsersActivity.this, AdminChatActivity.class);
                intent.putExtra("userIdCustom", selectedUser.getUserId());
                startActivity(intent);
            }
        });
    }

    private void displayUsers(List<UsersModel> users) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (UsersModel user : users) {
            adapter.add(user.getName());
        }
        listViewUsers.setAdapter(adapter);
    }
}
