package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PizzaResponseModel {

    @SerializedName("total")
    private Integer total;

    @SerializedName("currentPage")
    private Integer currentPage;

    @SerializedName("pizzas")
    private List<PizzaModel> pizzas;

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

    public List<PizzaModel> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<PizzaModel> pizzas) {
        this.pizzas = pizzas;
    }
}
