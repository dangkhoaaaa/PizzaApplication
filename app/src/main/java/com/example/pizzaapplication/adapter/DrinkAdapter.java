// DrinkAdapter.java
package com.example.pizzaapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Response.DrinkModel;

import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<DrinkModel> drinks = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemAddToCart(DrinkModel drink);
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
        private Button buttonAddToCart;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDrinkName = itemView.findViewById(R.id.textViewDrinkName);
            textViewDrinkDescription = itemView.findViewById(R.id.textViewDrinkDescription);
            textViewDrinkPrice = itemView.findViewById(R.id.textViewDrinkPrice);
            buttonAddToCart = itemView.findViewById(R.id.buttonAddToCart);
        }

        public void bind(final DrinkModel drink, final OnItemClickListener listener) {
            textViewDrinkName.setText(drink.getName());
            textViewDrinkDescription.setText(drink.getDescription());
            textViewDrinkPrice.setText(String.valueOf(drink.getPrice()));
            buttonAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemAddToCart(drink);
                }
            });
        }
    }
}
