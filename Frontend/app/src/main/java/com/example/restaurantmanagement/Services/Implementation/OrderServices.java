package com.example.restaurantmanagement.Services.Implementation;

import com.example.restaurantmanagement.Models.FoodItem;

import java.util.ArrayList;

public class OrderServices {
    public static ArrayList<FoodItem> getFoodItems() {
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        FoodItem f1 = new FoodItem(1,"Pan Fried Calamary","Appetizer", 3);
        FoodItem f2 = new FoodItem(2,"Chicken Popcorn","Appetizer", 2);
        FoodItem f3 = new FoodItem(3,"MushRoom Spagetti","Meal", 10);
        FoodItem f4 = new FoodItem(4,"Chicken Manchurian","Meal", 12.5);
        FoodItem f5 = new FoodItem(5,"Chocolate CheeseCake","Desserts", 4);
        FoodItem f6 = new FoodItem(6,"IceCream CheeseCake","Desserts", 4);
        FoodItem f7 = new FoodItem(7,"CocaCola","Drinks", 2);
        FoodItem f8 = new FoodItem(8,"Sprite","Drinks", 2);
        FoodItem f9 = new FoodItem(9,"Fried Rice","Meal", 9);
        FoodItem f10 = new FoodItem(1,"Pan Fried Calamary","Appetizer", 3);
        FoodItem f11 = new FoodItem(2,"Chicken Popcorn","Appetizer", 2);
        FoodItem f12 = new FoodItem(3,"MushRoom Spagetti","Meal", 10);
        FoodItem f13 = new FoodItem(4,"Chicken Manchurian","Meal", 12.5);
        foodItems.add(f1);
        foodItems.add(f2);
        foodItems.add(f3);
        foodItems.add(f4);
        foodItems.add(f5);
        foodItems.add(f6);
        foodItems.add(f7);
        foodItems.add(f8);
        foodItems.add(f9);
        foodItems.add(f10);
        foodItems.add(f11);
        foodItems.add(f12);
        foodItems.add(f13);
        return foodItems;
    }

    public static String[] getItemType() {
        String[] types = {"Appetizer", "Meal", "Desserts", "Drinks"};
        return types;
    }
}
