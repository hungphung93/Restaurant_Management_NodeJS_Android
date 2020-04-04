package com.example.restaurantmanagement.Models;

import com.example.restaurantmanagement.Enums.Role;
import com.google.gson.annotations.SerializedName;

public class GetOrderedFoodRequest {
    @SerializedName("role")
    private Role role;

    public GetOrderedFoodRequest(Role role){
        this.role = role;
    }

    public Role getRole(){
        return this.role;
    }
}
