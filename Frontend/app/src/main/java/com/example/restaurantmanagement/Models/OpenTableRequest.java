package com.example.restaurantmanagement.Models;

import com.google.gson.annotations.SerializedName;

public class OpenTableRequest {
    @SerializedName("tableName")
    private String tableName;

    public OpenTableRequest(String tableName){
        this.tableName = tableName;
    }

    public String getTableName(){
        return this.tableName;
    }
}
