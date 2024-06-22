package com.example.pizzaapplication.data.model.Request;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CustomerOrderRequestModel {

    @SerializedName("userId")
    private int userId;

    @SerializedName("customerPizzas")
    private List<CustomerPizzaRequestModel> customerPizzas;

    @SerializedName("customerDrinks")
    private List<CustomerDrinkRequestModel> customerDrinks;

    public CustomerOrderRequestModel() {
    }

    public CustomerOrderRequestModel(int userId, List<CustomerPizzaRequestModel> customerPizzas, List<CustomerDrinkRequestModel> customerDrinks) {
        this.userId = userId;
        this.customerPizzas = customerPizzas;
        this.customerDrinks = customerDrinks;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<CustomerPizzaRequestModel> getCustomerPizzas() {
        return customerPizzas;
    }

    public void setCustomerPizzas(List<CustomerPizzaRequestModel> customerPizzas) {
        this.customerPizzas = customerPizzas;
    }

    public List<CustomerDrinkRequestModel> getCustomerDrinks() {
        return customerDrinks;
    }

    public void setCustomerDrinks(List<CustomerDrinkRequestModel> customerDrinks) {
        this.customerDrinks = customerDrinks;
    }

    @Override
    public String toString() {
        return "CustomerOrderRequestModel{" +
                "userId=" + userId +
                ", customerPizzas=" + customerPizzas +
                ", customerDrinks=" + customerDrinks +
                '}';
    }
}
