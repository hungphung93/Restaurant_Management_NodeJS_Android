package com.example.restaurantmanagement.Models;

public class Order {
    private int id;
    private int image;
    private String orderMenuItem;
    private String tableName;
    private String orderStatus;

    public Order(int image, String orderMenuItem, String tableName, String orderStatus) {
        this.image = image;
        this.orderMenuItem = orderMenuItem;
        this.tableName = tableName;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getOrderMenuItem() {
        return orderMenuItem;
    }

    public void setOrderMenuItem(String orderMenuItem) {
        this.orderMenuItem = orderMenuItem;
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
