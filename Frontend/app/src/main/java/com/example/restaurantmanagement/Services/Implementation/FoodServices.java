package com.example.restaurantmanagement.Services.Implementation;

import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.FoodImage;
import com.example.restaurantmanagement.Services.Interface.IFoodServices;
import com.example.restaurantmanagement.Utilities.HttpHelper;

import java.util.ArrayList;

public class FoodServices {
    public static ApiResponse<ArrayList<FoodImage>> getAllFoodImages(){
        try{
            IFoodServices foodServices = HttpHelper.CreateApiService(IFoodServices.class);

            return new ApiResponse(foodServices.getAllFoodImages());

        }catch(Exception ex){
            throw ex;
        }
    }
}

