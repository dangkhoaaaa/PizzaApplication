package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailResponseModel {
    @SerializedName("orderId")
    private String orderId;

    @SerializedName("userName")
    private String userName;

    @SerializedName("dateTime")
    private String dateTime;

    @SerializedName("billDetails")
    private List<BillDetailModel> billDetails;

    @SerializedName("totalPrice")
    private Double totalPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<BillDetailModel> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetailModel> billDetails) {
        this.billDetails = billDetails;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
