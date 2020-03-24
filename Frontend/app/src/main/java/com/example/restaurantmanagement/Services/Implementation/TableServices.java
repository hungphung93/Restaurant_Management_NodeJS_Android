package com.example.restaurantmanagement.Services.Implementation;

import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.Services.Interface.IAuthServices;
import com.example.restaurantmanagement.Services.Interface.ITableServices;
import com.example.restaurantmanagement.Utilities.HttpHelper;

import java.util.ArrayList;

import io.reactivex.Observable;

public class TableServices {
    public static ApiResponse<ArrayList<Table>> getAllTables() {
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.getAllTables());

        }catch(Exception ex){
            throw ex;
        }
    }
}
