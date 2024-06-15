package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SizeResponseModel {

    @SerializedName("sizes")
    private List<SizeModel> sizes;

    // Getters and Setters


    public List<SizeModel> getSizeModels() {
        return sizes;
    }

    public void setSizeModels(List<SizeModel> sizes) {
        this.sizes = sizes;
    }
}
