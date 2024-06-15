package com.example.pizzaapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Request.CustomerPizzaRequestModel;

import java.util.List;

public class CartAdapter extends ArrayAdapter<CustomerPizzaRequestModel> {

    public CartAdapter(Context context, List<CustomerPizzaRequestModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomerPizzaRequestModel customerPizza = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
        }

        TextView textViewPizzaName = convertView.findViewById(R.id.textViewPizzaName);
        TextView textViewSize = convertView.findViewById(R.id.textViewSize);
        TextView textViewTopping = convertView.findViewById(R.id.textViewTopping);
        TextView textViewPrice = convertView.findViewById(R.id.textViewPrice);

        textViewPizzaName.setText("Pizza ID: " + customerPizza.getPizzaId());
        textViewSize.setText("Size ID: " + customerPizza.getSizeId());
        textViewTopping.setText("Topping ID: " + customerPizza.getToppingId());
        textViewPrice.setText(String.valueOf(customerPizza.getPrice()));

        return convertView;
    }
}
