package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ToppingResponseModel {

    @SerializedName("toppings")
    private List<ToppingModel> toppings;

    // Getters and Setters


    public List<ToppingModel> getToppingModels() {
        return toppings;
    }

    public void setToppingModels(List<ToppingModel> toppings) {
        this.toppings = toppings;
    }
}
