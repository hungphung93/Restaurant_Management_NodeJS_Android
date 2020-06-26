package com.example.restaurantmanagement.Models;

import com.example.restaurantmanagement.Utilities.DateTimeHelper;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class Table {
    @SerializedName("tableId")
    private String tableId;
    @SerializedName("tableName")
    private String tableName;
    @SerializedName("tableStatus")
    private String tableStatus;
    @SerializedName("openAt")
    private Date openAt;

    public Table(){}

    public Table(String tableId, String tableName, String tableStatus, Date openAt){
        this.tableId = tableId;
        this.tableName = tableName;
        this.tableStatus = tableStatus;
        this.openAt = openAt;
    }

    public String getTableId(){
        return this.tableId;
    }

    public void setTableId(String tableId){
        this.tableId = tableId;
    }

    public String getTableName(){
        return this.tableName;
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public String getTableStatus(){
        return this.tableStatus;
    }

    public void setTableStatus(String tableStatus){
        this.tableStatus = tableStatus;
    }

    public String getOpenAt(){
        return DateTimeHelper.getTime(this.openAt);
    }

    public void setOpenAt(Date openAt){
        this.openAt = openAt;
    }
}
