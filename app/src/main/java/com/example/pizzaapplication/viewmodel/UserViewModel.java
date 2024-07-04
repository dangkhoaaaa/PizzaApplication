package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.model.Response.UsersResponseModel;
import com.example.pizzaapplication.data.repository.PizzaRepository;
import com.example.pizzaapplication.data.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserViewModel extends ViewModel {
    private MutableLiveData<UsersResponseModel> users;
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        users = new MutableLiveData<>();
        this.userRepository = userRepository;
        fetchUsers();
    }

    public LiveData<UsersResponseModel> getUsers() {
        return users;
    }

    private void fetchUsers() {
        userRepository.getUsers(new Callback<UsersResponseModel>() {
            @Override
            public void onResponse(Call<UsersResponseModel> call, Response<UsersResponseModel> response) {
                if (response.isSuccessful()) {
                    users.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UsersResponseModel> call, Throwable t) {
                // Handle failure
            }
        });
    }

}
