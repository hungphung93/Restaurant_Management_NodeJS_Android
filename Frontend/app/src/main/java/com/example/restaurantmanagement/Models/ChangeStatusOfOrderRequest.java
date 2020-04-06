package com.example.restaurantmanagement.Models;

import com.example.restaurantmanagement.Enums.OrderStatus;
import com.google.gson.annotations.SerializedName;

public class ChangeStatusOfOrderRequest {
    @SerializedName("transactionId")
    public String transactionId;

    @SerializedName("orderId")
    public String orderId;

    @SerializedName("status")
    public String status;

    public ChangeStatusOfOrderRequest(String transactionId, String orderId, String status){
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.status = status;
    }
}
