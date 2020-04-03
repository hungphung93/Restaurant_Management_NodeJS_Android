package com.example.restaurantmanagement.Models;

import com.google.gson.annotations.SerializedName;

public class FoodImage {
    @SerializedName("foodId")
    private String foodId;
    @SerializedName("image_url")
    private String imageURL;

    public String getFoodId() { return this.foodId; }

    public void setFoodId(String foodId){ this.foodId = foodId;}

    public String getImageURL(){ return this.imageURL; }

    public void setImageURL(String imageURL){ this.imageURL = imageURL; }
}
