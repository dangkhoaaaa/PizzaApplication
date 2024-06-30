package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponseModel {
    @SerializedName("total")
    private Integer total;

    @SerializedName("currentPage")
    private Integer currentPage;

    @SerializedName("users")
    private List<UsersModel> users;

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

    public List<UsersModel> getUsers() {
        return users;
    }

    public void setUsers(List<UsersModel> drinks) {
        this.users = drinks;
    }


}
