package com.example.restaurantmanagement.Services.Interface;

import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.FoodImage;
import com.example.restaurantmanagement.Models.FoodItem;
import com.example.restaurantmanagement.Models.Table;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface IFoodServices {
    @POST("food/getAllFoodImages")
    Observable<BaseResponse<ArrayList<FoodImage>>> getAllFoodImages();



    @POST("food/getAllFoods")
    Observable<BaseResponse<ArrayList<FoodItem>>> getAllFoods();
}
