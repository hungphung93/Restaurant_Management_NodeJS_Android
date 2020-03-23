package com.example.restaurantmanagement.Models;

public class LoggingUser {
    private static UserInfo userInfo;

    public static UserInfo getUserInfo(){
        return userInfo;
    }

    public static void setUserInfo(UserInfo info){
        userInfo = info;
    }
}
