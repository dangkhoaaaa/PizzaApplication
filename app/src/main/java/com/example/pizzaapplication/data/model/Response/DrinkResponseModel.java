package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DrinkResponseModel {

    @SerializedName("total")
    private Integer total;

    @SerializedName("currentPage")
    private Integer currentPage;

    @SerializedName("drinks")
    private List<DrinkModel> drinks;

    // Getters and Setters
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<DrinkModel> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkModel> drinks) {
        this.drinks = drinks;
    }
}
