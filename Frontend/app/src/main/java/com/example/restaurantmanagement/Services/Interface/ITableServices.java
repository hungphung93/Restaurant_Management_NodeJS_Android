package com.example.restaurantmanagement.Services.Interface;

import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.Table;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ITableServices {

    @POST("table/getAllTables")
    Observable<BaseResponse<ArrayList<Table>>> getAllTables();
}
