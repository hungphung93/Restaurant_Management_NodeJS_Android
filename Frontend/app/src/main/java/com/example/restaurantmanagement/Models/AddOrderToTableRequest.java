package com.example.restaurantmanagement.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddOrderToTableRequest {
    @SerializedName("tableName")
    public String tableName;

    @SerializedName("listFood")
    public ArrayList<FoodItem> listFood;

    public AddOrderToTableRequest(String tableName, ArrayList<FoodItem> listFood){
        this.tableName = tableName;
        this.listFood = listFood;
    }

}
