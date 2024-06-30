package com.example.pizzaapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Drink;
import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.utils.Utils;

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
        if (item instanceof Pizza) {
            return VIEW_TYPE_PIZZA;
        } else if (item instanceof Drink) {
            return VIEW_TYPE_DRINK;
        } else {
            throw new IllegalArgumentException("Unknown item type in cart: " + item.getClass().getName());
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Pizza and Drink
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        Object item = getItem(position);

        if (convertView == null) {
            if (viewType == VIEW_TYPE_PIZZA) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_drink, parent, false);
            }
        }

        if (viewType == VIEW_TYPE_PIZZA) {
            Pizza pizza = (Pizza) item;
            TextView textViewPizzaName = convertView.findViewById(R.id.textViewPizzaName);
            TextView textViewSize = convertView.findViewById(R.id.textViewSize);
            TextView textViewTopping = convertView.findViewById(R.id.textViewTopping);
            TextView textViewPrice = convertView.findViewById(R.id.textViewPrice);
            TextView textViewQuantity = convertView.findViewById(R.id.textViewQuantityPizza);
            ImageView imageViewPizza = convertView.findViewById(R.id.imageViewPizza);

            textViewPizzaName.setText(pizza.getName());
            textViewSize.setText("Size: " + pizza.getSize());
            textViewTopping.setText("Topping: " + pizza.getTopping());
            textViewQuantity.setText("Quantity: " + pizza.getQuantity());
            textViewPrice.setText("Price: " + Utils.formattedPrice(pizza.getPrice())+"đ");
            String imageUrl = pizza.getImg();  // Assuming getImg() is a method in the Pizza class

            // Use Glide library for efficient image loading (recommended)
            Glide.with(convertView.getContext())
                    .load(imageUrl)
                    .into(imageViewPizza);

        } else {
            Drink drink = (Drink) item;
            TextView textViewDrinkName = convertView.findViewById(R.id.textViewDrinkName);
            TextView textViewDrinkPrice = convertView.findViewById(R.id.textViewDrinkPrice);
            TextView textViewQuantity = convertView.findViewById(R.id.textViewQuantityDrink);
            ImageView imageViewDrink = convertView.findViewById(R.id.imageViewDrink);


            textViewDrinkName.setText(drink.getName());
            textViewDrinkPrice.setText("Price: " + Utils.formattedPrice(drink.getPrice())+"đ");
            textViewQuantity.setText("Quantity: " +drink .getQuantity());
            String imageUrl = drink.getImg();  // Assuming getImg() is a method in the Pizza class

            // Use Glide library for efficient image loading (recommended)
            Glide.with(convertView.getContext())
                    .load(imageUrl)
                    .into(imageViewDrink);
        }

        return convertView;
    }
}
