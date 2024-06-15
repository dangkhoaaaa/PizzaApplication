package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;

public class CustomerPizza {

    @SerializedName("id")
    private int id;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("pizzaId")
    private int pizzaId;

    @SerializedName("sizeId")
    private int sizeId;

    @SerializedName("customerOrder")
    private CustomerOrder customerOrder;

    @SerializedName("pizza")
    private Pizza pizza;

    @SerializedName("size")
    private Size size;

    @SerializedName("topping")
    private Topping topping;

    // Default constructor
    public CustomerPizza() {
        this.id = 0;
        this.orderId = 0;
        this.pizzaId = 0;
        this.sizeId = 0;
        this.customerOrder = null; // or initialize as new CustomerOrder();
        this.pizza = null; // or initialize as new Pizza();
        this.size = null; // or initialize as new Size();
        this.topping = null; // or initialize as new Topping();
    }

    // Parameterized constructor
    public CustomerPizza(int id, int orderId, int pizzaId, int sizeId, CustomerOrder customerOrder, Pizza pizza, Size size, Topping topping) {
        this.id = id;
        this.orderId = orderId;
        this.pizzaId = pizzaId;
        this.sizeId = sizeId;
        this.customerOrder = customerOrder;
        this.pizza = pizza;
        this.size = size;
        this.topping = topping;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    // toString method to print CustomerPizza object details
    @Override
    public String toString() {
        return "CustomerPizza{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", pizzaId=" + pizzaId +
                ", sizeId=" + sizeId +
                ", customerOrder=" + customerOrder +
                ", pizza=" + pizza +
                ", size=" + size +
                ", topping=" + topping +
                '}';
    }
}
