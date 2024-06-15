package com.example.pizzaapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Response.PizzaModel;

import java.util.ArrayList;
import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private List<PizzaModel> pizzas = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(PizzaModel pizza);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setPizzas(List<PizzaModel> pizzas) {
        this.pizzas = pizzas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        PizzaModel pizza = pizzas.get(position);
        holder.bind(pizza, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    class PizzaViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewPizzaName;
        private TextView textViewPizzaDescription;
        private TextView textViewPizzaPrice;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPizzaName = itemView.findViewById(R.id.textViewPizzaName);
            textViewPizzaDescription = itemView.findViewById(R.id.textViewPizzaDescription);
            textViewPizzaPrice = itemView.findViewById(R.id.textViewPizzaPrice);
        }

        public void bind(final PizzaModel pizza, final OnItemClickListener listener) {
            textViewPizzaName.setText(pizza.getName());
            textViewPizzaDescription.setText(pizza.getDescription());
            textViewPizzaPrice.setText(String.valueOf(pizza.getPrice()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pizza);
                }
            });
        }
    }
}
