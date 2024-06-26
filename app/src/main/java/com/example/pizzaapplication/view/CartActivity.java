package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNameMap;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.CartAdapter;
import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.CustomerDrinkRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerOrderRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;

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

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;
    private Button buttonCheckout;
    private boolean isCheckoutInProgress = false;

    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "DangKhoa";
    private String description = "Thanh toán dịch vụ Pizza FPT";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION

        listViewCart = findViewById(R.id.listViewCart);
        buttonCheckout = findViewById(R.id.buttonCheckout);

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
                    processCheckout(pizzaCart, drinkCart);
                    requestPayment();
                } else {
                    Toast.makeText(CartActivity.this, "Checkout is already in progress", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void processCheckout(List<CustomerPizzaRequestModel> pizzaCart, List<CustomerDrinkRequestModel> drinkCart) {
        // Construct the order request model
        CustomerOrderRequestModel orderRequest = new CustomerOrderRequestModel();
        orderRequest.setUserId(1); // Replace with actual user ID
        orderRequest.setCustomerPizzas(pizzaCart);
        orderRequest.setCustomerDrinks(drinkCart);

        // Make the API call
        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse<Integer>> call = apiService.createOrder(orderRequest);

        call.enqueue(new Callback<ApiResponse<Integer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                isCheckoutInProgress = false;
                if (response.isSuccessful() && response.body() != null && response.body().getIsSuccess()) {
                    int customerOrderId = response.body().getData();
                    Toast.makeText(CartActivity.this, "Order created successfully. Order ID: " + customerOrderId, Toast.LENGTH_LONG).show();
                } else {
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
    //Get token through MoMo app
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);


        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", "orderId123456785"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", amount); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
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
    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    //   tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                        Toast.makeText(CartActivity.this, "MOMO SUSSESS FULLL " , Toast.LENGTH_LONG).show();
                    } else {
                        //  tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    //  tvMessage.setText("message: " + message);
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    //   tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                } else {
                    //TOKEN FAIL
                    //   tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                }
            } else {
                //  tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
            }
        } else {
            //  tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
        }
    }

}
