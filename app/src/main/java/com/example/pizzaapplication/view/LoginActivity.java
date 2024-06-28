package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.TokenResponse;
import com.example.pizzaapplication.data.repository.UserRepository;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.utils.JwtUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewCreateAccount = findViewById(R.id.textViewCreateAccount);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle login logic here
                    loginUser(email, password);
                }
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forgot password logic here
                Toast.makeText(LoginActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
            }
        });

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle create new account logic here
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        UserRepository userRepository = new UserRepository(apiService);

        // Thực hiện gọi API login
        userRepository.login(email, password, new UserRepository.LoginCallback() {
            @Override
            public void onSuccess(TokenResponse tokenResponse) {
                // Đăng nhập thành công, lưu token vào SharedPreferences (ví dụ)
                String token = tokenResponse.getToken();
                String userId = JwtUtils.getUserId(token);
                DataLocalManager.setToken(token);
                DataLocalManager.setUserId(userId);


                // Chuyển sang MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String message) {
                // Xử lý lỗi đăng nhập
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
