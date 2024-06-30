package com.example.pizzaapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Response.DrinkModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminDrinkAdapter extends RecyclerView.Adapter<AdminDrinkAdapter.DrinkViewHolder> {

    private List<DrinkModel> drinks = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(DrinkModel drink);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink_admin, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkModel drink = drinks.get(position);
        holder.drinkName.setText(drink.getName());
        holder.drinkDescription.setText(drink.getDescription());
        holder.drinkPrice.setText(String.valueOf(drink.getPrice()));
        Picasso.get().load(drink.getImage()).into(holder.drinkImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(drink);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public void setDrinks(List<DrinkModel> drinks) {
        this.drinks = drinks;
        notifyDataSetChanged();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        TextView drinkName, drinkDescription, drinkPrice;
        ImageView drinkImage;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.tvDrinkNameAdmin);
            drinkDescription = itemView.findViewById(R.id.textViewDrinkDescriptionAdmin);
            drinkPrice = itemView.findViewById(R.id.tvDrinkPriceAdmin);
            drinkImage = itemView.findViewById(R.id.imageViewDrinkAdmin);
        }
    }
}
