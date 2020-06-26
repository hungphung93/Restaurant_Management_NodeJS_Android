package com.example.restaurantmanagement.EventListenerInterface;


import com.example.restaurantmanagement.Models.Order;

public interface IOrderedFoodListEventListener {
    void onOrderedFoodClick(Order orderedFood);
}
