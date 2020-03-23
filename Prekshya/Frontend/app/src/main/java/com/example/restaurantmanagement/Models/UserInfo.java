package com.example.restaurantmanagement.Models;


import com.example.restaurantmanagement.Enums.Role;
import com.google.gson.annotations.SerializedName;

public class UserInfo{
    @SerializedName("_id")
    private String userId;
    @SerializedName("username")
    private String username;

    //private String displayName;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("role")
    private String sRole;

    public UserInfo(){}

    public UserInfo(String userId, String username, String displayName, String accessToken, Role role){
        this.userId = userId;
        this.username = username;
        //this.displayName = displayName;
        this.accessToken = accessToken;
        this.sRole = role.toString();
    }

    public String GetUserId(){
        return this.userId;
    }

    public void SetUserId(String userId){
        this.userId = userId;
    }

    public String GetUsername(){
        return this.username;
    }

    public void SetUsername(String username){
        this.username = username;
    }

    public String GetDisplayName(){
        return "";
        //return this.displayName;
    }

    public void SetDisplayName(String displayName){
        //this.displayName = displayName;
    }

    public String GetAccessToken(){
        return  this.accessToken;
    }

    public void SetAccessToken() {
        this.accessToken = accessToken;
    }

    public Role GetRole(){
        return Role.valueOf(this.sRole);
    }

    public void SetRole(Role role){
        this.sRole = role.toString();
    }
}
