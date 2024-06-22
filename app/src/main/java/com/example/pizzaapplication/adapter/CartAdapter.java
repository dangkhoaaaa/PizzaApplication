package com.example.pizzaapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;
import com.example.pizzaapplication.data.model.Request.CustomerDrinkRequestModel;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Object> {

    private static final int VIEW_TYPE_PIZZA = 0;
    private static final int VIEW_TYPE_DRINK = 1;

    public CartAdapter(Context context, List<Object> objects) {
        super(context, 0, objects);
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        if (item instanceof CustomerPizzaRequestModel) {
            return VIEW_TYPE_PIZZA;
        } else {
            return VIEW_TYPE_DRINK;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Pizza and Drink
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);

        if (convertView == null) {
            if (viewType == VIEW_TYPE_PIZZA) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_drink, parent, false);
            }
        }

        if (viewType == VIEW_TYPE_PIZZA) {
            CustomerPizzaRequestModel pizza = (CustomerPizzaRequestModel) getItem(position);
            TextView textViewPizzaName = convertView.findViewById(R.id.textViewPizzaName);
            TextView textViewSize = convertView.findViewById(R.id.textViewSize);
            TextView textViewTopping = convertView.findViewById(R.id.textViewTopping);
            TextView textViewPrice = convertView.findViewById(R.id.textViewPrice);

            textViewPizzaName.setText("Pizza ID: " + pizza.getPizzaId());
            textViewSize.setText("Size ID: " + pizza.getSizeId());
            textViewTopping.setText("Topping ID: " + pizza.getToppingId());
            textViewPrice.setText(String.valueOf(pizza.getPrice()));
        } else {
            CustomerDrinkRequestModel drink = (CustomerDrinkRequestModel) getItem(position);
            TextView textViewDrinkName = convertView.findViewById(R.id.textViewDrinkName);
            TextView textViewDrinkPrice = convertView.findViewById(R.id.textViewDrinkPrice);

            textViewDrinkName.setText("Drink ID: " + drink.getDrinkId());
            textViewDrinkPrice.setText(String.valueOf(drink.getPrice()));
        }

        return convertView;
    }
}
