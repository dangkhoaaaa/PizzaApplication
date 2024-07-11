package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.pizzaapplication.view.admin.AdminActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private SignInButton buttonGoogleSignIn;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";
    private TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonGoogleSignIn = findViewById(R.id.buttonGoogleSignIn);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        DataLocalManager.init(this);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });

        buttonGoogleSignIn.setOnClickListener(v -> signInWithGoogle());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("787657429754-3ii24krbtnre6h1cv52m9d062hqg80cq.apps.googleusercontent.com")
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void loginUser(String email, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        UserRepository userRepository = new UserRepository(apiService);

        userRepository.login(email, password, new UserRepository.LoginCallback() {
            @Override
            public void onSuccess(TokenResponse tokenResponse) {
                String token = tokenResponse.getToken();
                String userId = JwtUtils.getUserId(token);
                String role = JwtUtils.getRole(token);

                if (Objects.equals(role, "1")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    DataLocalManager.setToken(token);
                    DataLocalManager.setUserId(userId);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(LoginActivity.this, "Login failed: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                String idToken = account.getIdToken();
                String email = account.getEmail();
                String name = account.getDisplayName();
                String avatar = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : null;
                String address = "a"; // Điền thông tin địa chỉ của người dùng ở đây
                String phone = "23123123"; // Điền thông tin số điện thoại của người dùng ở đây
                // Now you have the email, name, and avatar
                // Send ID token to server and validate
                loginUserWithGoogle(address, email, name, phone);
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Failed to sign in with Google: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void loginUserWithGoogle(String adress, String email, String name, String phone) {
        ApiService apiService = RetrofitClient.getApiService();
        UserRepository userRepository = new UserRepository(apiService);

        userRepository.loginWithGoogle(adress, email, name, phone, new UserRepository.LoginCallback() {
            @Override
            public void onSuccess(TokenResponse tokenResponse) {
                String token = tokenResponse.getToken();
                String userId = JwtUtils.getUserId(token);
                String role = JwtUtils.getRole(token);

                if (Objects.equals(role, "1")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    DataLocalManager.setToken(token);
                    DataLocalManager.setUserId(userId);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(LoginActivity.this, "Login failed: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
