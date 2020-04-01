package com.example.restaurantmanagement.Services.Implementation;

import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;

public class TransactionServices {
    public static ArrayList<Order> getAllCurrentTransactionFoods(){
        ArrayList<Order> orders = new ArrayList<>();

        orders.add(new Order(R.drawable.img_ice_cream, "Ice Cream", "Table 7", "Served"));
        orders.add(new Order(R.drawable.img_rootbeer, "Root Beer", "Table 5", "Ready"));
        orders.add(new Order(R.drawable.img_chicken_popcorn, "Chicken Popcorn", "Table 3", "Ready"));
        orders.add(new Order(R.drawable.img_cheesecake,"Chocolate Cheesecake", "Table 5", "Cooking"));
        orders.add(new Order(R.drawable.img_chicken_manchurian, "Chicken Manchurian", "Table 3", "On Line"));
        orders.add(new Order(R.drawable.img_iced_tea, "Ice Tea", "Table 4", "Ready"));
        orders.add(new Order(R.drawable.img_french_fries, "French Fries", "Table 5", "Cooking"));
        orders.add(new Order(R.drawable.img_chicken_popcorn,"Fish Popcorn", "Table 1", "Ready"));
        orders.add(new Order(R.drawable.img_iced_tea, "Iced Tea", "Table 8", "Served"));
        orders.add(new Order(R.drawable.img_wine, "Red wine", "Table 9", "On Line"));
        orders.add(new Order(R.drawable.img_ramen, "Ramen", "Table 10", "Cooking"));

        return orders;
    }
}
