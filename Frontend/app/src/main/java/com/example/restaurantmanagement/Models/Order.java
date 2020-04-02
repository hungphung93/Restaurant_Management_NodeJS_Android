package com.example.restaurantmanagement.Models;

public class Order {
    private String id;
    private int image;
    private String foodName;
    private String tableName;
    private String orderStatus;
    private int quantity;
    private double price;

    public Order(int image, String foodName, String tableName, String orderStatus) {
        this.image = image;
        this.foodName = foodName;
        this.tableName = tableName;
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setOrderMenuItem(String foodName) {
        this.foodName = foodName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
