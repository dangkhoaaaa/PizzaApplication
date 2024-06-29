package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.CartAdapter;
import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.CustomerDrinkRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerOrderRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.utils.JwtUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNameMap;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;
    private Button buttonCheckout;
    private boolean isCheckoutInProgress = false;

    private String fee = "10000";
    int environment = 0; // developer default
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "DangKhoa";
    private String description = "Thanh toán dịch vụ Pizza FPT";

    private int customerOrderId;
    private double totalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION

        listViewCart = findViewById(R.id.listViewCart);
        buttonCheckout = findViewById(R.id.buttonCheckout);

        // Check if the token exists
        String token = DataLocalManager.getInstance().getToken();
        if (token == null) {
            // Redirect to login activity if no token is found
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity to prevent the user from going back
            return;
        }

        // Extract user ID from token
        String userId = JwtUtils.getUserId(token);
        if (userId == null) {
            Toast.makeText(this, "Invalid token. Please log in again.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        List<CustomerPizzaRequestModel> pizzaCart = PizzaDetailActivity.getCart();
        List<CustomerDrinkRequestModel> drinkCart = DrinkFragment.getDrinkCart();

        if ((pizzaCart != null && !pizzaCart.isEmpty()) || (drinkCart != null && !drinkCart.isEmpty())) {
            List<Object> cartItems = new ArrayList<>();
            if (pizzaCart != null) {
                cartItems.addAll(pizzaCart);
            }
            if (drinkCart != null) {
                cartItems.addAll(drinkCart);
            }

            CartAdapter adapter = new CartAdapter(this, cartItems);
            listViewCart.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        }

        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheckoutInProgress) {
                    isCheckoutInProgress = true;
                    totalPrice = calculateTotalPrice(pizzaCart, drinkCart);
                    processCheckout(userId, pizzaCart, drinkCart, totalPrice);
                } else {
                    Toast.makeText(CartActivity.this, "Checkout is already in progress", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private double calculateTotalPrice(List<CustomerPizzaRequestModel> pizzaCart, List<CustomerDrinkRequestModel> drinkCart) {
        double totalPrice = 0;
        if (pizzaCart != null) {
            for (CustomerPizzaRequestModel pizza : pizzaCart) {
                totalPrice += pizza.getPrice() * pizza.getQuantity();
            }
        }
        if (drinkCart != null) {
            for (CustomerDrinkRequestModel drink : drinkCart) {
                totalPrice += drink.getPrice() * drink.getQuantity();
            }
        }
        return totalPrice;
    }

    private void processCheckout(String userId, List<CustomerPizzaRequestModel> pizzaCart, List<CustomerDrinkRequestModel> drinkCart, double totalPrice) {
        // Construct the order request model
        CustomerOrderRequestModel orderRequest = new CustomerOrderRequestModel();
        orderRequest.setUserId(Integer.parseInt(userId)); // Set the user ID from token
        orderRequest.setCustomerPizzas(pizzaCart);
        orderRequest.setCustomerDrinks(drinkCart);

        // Make the API call
        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse<Integer>> call = apiService.createOrder(orderRequest);

        call.enqueue(new Callback<ApiResponse<Integer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getIsSuccess()) {
                    customerOrderId = response.body().getData();
                    Toast.makeText(CartActivity.this, "Order created successfully. Order ID: " + customerOrderId, Toast.LENGTH_LONG).show();
                    requestPayment(totalPrice, customerOrderId);
                } else {
                    isCheckoutInProgress = false;
                    Toast.makeText(CartActivity.this, "Failed to create order", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Integer>> call, Throwable t) {
                isCheckoutInProgress = false;
                Toast.makeText(CartActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Get token through MoMo app
    private void requestPayment(double amount, int customerOrderId) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        // client Required
        eventValue.put("merchantname", merchantName); // Tên đối tác
        eventValue.put("merchantcode", merchantCode); // Mã đối tác
        eventValue.put("amount", amount); // Kiểu integer
        eventValue.put("orderId", String.valueOf(customerOrderId)); // unique id cho Bill order
        eventValue.put("orderLabel", "Mã đơn hàng"); // gán nhãn

        // client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ"); // gán nhãn
        eventValue.put("fee", fee); // Kiểu integer
        eventValue.put("description", description); // mô tả đơn hàng

        // client extra data
        eventValue.put("requestId", merchantCode + "merchant_billId_" + System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        // Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }

    // Get token callback from MoMo app and submit to server side
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    // TOKEN IS AVAILABLE
                    String token = data.getStringExtra("data"); // Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if (env == null) {
                        env = "app";
                    }

                    if (token != null && !token.equals("")) {
                        // Send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                        Toast.makeText(CartActivity.this, "MOMO SUCCESSFUL", Toast.LENGTH_LONG).show();
                        showPaymentSuccessPopup(customerOrderId, totalPrice);
                    } else {
                        // Handle token not received
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    // TOKEN FAIL
                    // Handle token failure
                } else if (data.getIntExtra("status", -1) == 2) {
                    // TOKEN FAIL
                    // Handle token failure
                } else {
                    // TOKEN FAIL
                    // Handle token failure
                }
            } else {
                // Handle token not received
            }
        } else {
            // Handle token not received
        }
    }
    private void showPaymentSuccessPopup(int orderId, double totalPrice) {
        // Inflate the custom layout/view
        View view = getLayoutInflater().inflate(R.layout.popup_payment_success, null);

        // Create the AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        // Initialize the UI components in the popup
        TextView textViewOrderSummary = view.findViewById(R.id.textViewOrderSummary);
        TextView textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice);
        Button buttonReturnToMain = view.findViewById(R.id.buttonReturnToMain);

        // Set the order details and total price
        String orderSummary = "Order ID: " + orderId + "\nOrder Details: " + getOrderDetails();
        textViewOrderSummary.setText(orderSummary);
        textViewTotalPrice.setText("Total: " + totalPrice + " VND");

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set the button click listener
        buttonReturnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Clear the cart UI
                clearCartUI();
                // Return to main activity
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getOrderDetails() {
        // Construct a string with the order details
        // This should include the items, quantities, and prices
        // You can format it as needed
        // For example:
        StringBuilder details = new StringBuilder();
        for (CustomerPizzaRequestModel pizza : PizzaDetailActivity.getCart()) {
            details.append("Pizza: ").append(pizza.getPizzaId())
                    .append(", Quantity: ").append(pizza.getQuantity())
                    .append(", Price: ").append(pizza.getPrice()).append("\n");
        }
        for (CustomerDrinkRequestModel drink : DrinkFragment.getDrinkCart()) {
            details.append("Drink: ").append(drink.getDrinkId())
                    .append(", Quantity: ").append(drink.getQuantity())
                    .append(", Price: ").append(drink.getPrice()).append("\n");
        }
        return details.toString();
    }

    private void clearCartUI() {
        // Clear the cart items from the UI
        listViewCart.setAdapter(null);
    }
}
