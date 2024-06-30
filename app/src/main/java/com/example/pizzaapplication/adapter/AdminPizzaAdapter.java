package com.example.pizzaapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Response.PizzaModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminPizzaAdapter extends RecyclerView.Adapter<AdminPizzaAdapter.PizzaViewHolder> {

    private List<PizzaModel> pizzas = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(PizzaModel pizza);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza_admin, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        PizzaModel pizza = pizzas.get(position);
        holder.pizzaName.setText(pizza.getName());
        holder.pizzaDescription.setText(pizza.getDescription());
        holder.pizzaPrice.setText(String.valueOf(pizza.getPrice()));
        Picasso.get().load(pizza.getImage()).into(holder.pizzaImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(pizza);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public void setPizzas(List<PizzaModel> pizzas) {
        this.pizzas = pizzas;
        notifyDataSetChanged();
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName, pizzaDescription, pizzaPrice;
        ImageView pizzaImage;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.tvPizzaNameAdmin);
            pizzaDescription = itemView.findViewById(R.id.textViewPizzaDescriptionAdmin);
            pizzaPrice = itemView.findViewById(R.id.tvPizzaPriceAdmin);
            pizzaImage = itemView.findViewById(R.id.imageViewPizzaAdmin);
        }
    }
}
