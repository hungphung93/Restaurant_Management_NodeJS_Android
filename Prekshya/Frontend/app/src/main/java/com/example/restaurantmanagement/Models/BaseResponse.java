package com.example.restaurantmanagement.Models;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    public boolean IsSuccess(){
        return isSuccess;
    }

    public String GetMessage(){
        return this.message;
    }

    public T GetData(){
        return data;
    }
}
