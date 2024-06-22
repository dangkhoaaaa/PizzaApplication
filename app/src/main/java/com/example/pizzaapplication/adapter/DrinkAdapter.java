package com.example.pizzaapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Response.DrinkModel;

import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<DrinkModel> drinks = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemAddToCart(DrinkModel drink, int quantity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setDrinks(List<DrinkModel> drinks) {
        this.drinks = drinks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkModel drink = drinks.get(position);
        holder.bind(drink, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    class DrinkViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDrinkName;
        private TextView textViewDrinkDescription;
        private TextView textViewDrinkPrice;
        private ImageView imageViewDrink;
        private Button buttonAddToCart;
        private Button buttonIncreaseQuantity;
        private Button buttonDecreaseQuantity;
        private TextView textViewQuantity;

        private int quantity = 1;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDrinkName = itemView.findViewById(R.id.textViewDrinkName);
            textViewDrinkDescription = itemView.findViewById(R.id.textViewDrinkDescription);
            textViewDrinkPrice = itemView.findViewById(R.id.textViewDrinkPrice);
            imageViewDrink = itemView.findViewById(R.id.imageViewDrink);
            buttonAddToCart = itemView.findViewById(R.id.buttonAddToCart);
            buttonIncreaseQuantity = itemView.findViewById(R.id.buttonIncreaseQuantity);
            buttonDecreaseQuantity = itemView.findViewById(R.id.buttonDecreaseQuantity);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
        }

        public void bind(final DrinkModel drink, final OnItemClickListener listener) {
            textViewDrinkName.setText(drink.getName());
            textViewDrinkDescription.setText(drink.getDescription());
            textViewDrinkPrice.setText(String.valueOf(drink.getPrice()));

            // Load image using Glide
            Glide.with(itemView.getContext())
                    .load(drink.getImage())
                    .into(imageViewDrink);

            buttonIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity++;
                    textViewQuantity.setText(String.valueOf(quantity));
                }
            });

            buttonDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (quantity > 1) {
                        quantity--;
                        textViewQuantity.setText(String.valueOf(quantity));
                    }
                }
            });

            buttonAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemAddToCart(drink, quantity);
                }
            });
        }
    }
}
