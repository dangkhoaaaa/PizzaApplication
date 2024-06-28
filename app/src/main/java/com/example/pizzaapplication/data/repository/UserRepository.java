package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Request.RegisterRequestModel;
import com.example.pizzaapplication.data.model.Response.RegisterResponse;
import com.example.pizzaapplication.data.model.Response.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;

    public UserRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void login(String email, String password, final LoginCallback callback) {
        Call<TokenResponse> call = apiService.login(email, password);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    if (tokenResponse != null) {
                        callback.onSuccess(tokenResponse);
                    } else {
                        callback.onError("Empty response body");
                    }
                } else {
                    callback.onError("Login failed, error message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                callback.onError("Error occurred while calling login API: " + t.getMessage());
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(TokenResponse tokenResponse);

        void onError(String message);
    }

    public void registerUser(RegisterRequestModel user, final RegisterCallback callback) {
        Call<RegisterResponse> call = apiService.register(user);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse != null) {
                        callback.onSuccess(registerResponse);
                    } else {
                        callback.onError("Empty response body");
                    }
                } else {
                    callback.onError("Register failed, error message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onError("Error occurred while calling register API: " + t.getMessage());
            }
        });
    }
    public interface RegisterCallback {
        void onSuccess(RegisterResponse registerResponse);
        void onError(String message);
    }


}
