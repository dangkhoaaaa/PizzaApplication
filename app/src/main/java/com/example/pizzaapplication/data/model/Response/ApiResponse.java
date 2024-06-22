package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("data")
    private T data;

    public String getMessage() {
        return message;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public T getData() {
        return data;
    }
}
