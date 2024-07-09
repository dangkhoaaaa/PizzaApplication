package com.example.pizzaapplication.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Request.CustomerDrinkRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.view.DrinkFragment;
import com.example.pizzaapplication.view.LoginActivity;
import com.example.pizzaapplication.view.MainActivity;
import com.example.pizzaapplication.view.PizzaDetailActivity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";

    // Method to format date of birth
    public static String formatDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return ""; // Handle empty date
        }

        try {
            // Parse the date from the API response format (assuming ISO 8601)
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = inputFormat.parse(dateOfBirth);

            // Format the date to the desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date of birth: " + e.getMessage());
            return dateOfBirth; // Return original value if parsing fails
        }
    }

    // Method to validate phone number
    public static boolean isValidPhoneNumber(String phone) {
        // Improved phone number validation using regex
        String phoneRegex = "^0[0-9]{9}$";
        return phone.matches(phoneRegex);
    }

    public static void showLoginPrompt(Context context, String message)  {
        new AlertDialog.Builder(context)
                .setTitle("Login Required")
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //check login
    public static boolean isLoggedIn() {
        String token = DataLocalManager.getInstance().getToken();
        return token != null && !token.isEmpty();
    }

    public static boolean isCartEmpty() {
        List<CustomerPizzaRequestModel> pizzaCart = PizzaDetailActivity.getCart();
        List<CustomerDrinkRequestModel> drinkCart = DrinkFragment.getDrinkCart();
        return (pizzaCart == null || pizzaCart.isEmpty()) && (drinkCart == null || drinkCart.isEmpty());
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String formattedPrice(double price){
        NumberFormat formatter = NumberFormat.getInstance();
        String formattedPrice = formatter.format(price);
        return formattedPrice;
    }

}
