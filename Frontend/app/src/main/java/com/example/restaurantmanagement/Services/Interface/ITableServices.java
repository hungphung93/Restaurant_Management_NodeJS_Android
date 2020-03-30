package com.example.restaurantmanagement.Services.Interface;

import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Table;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ITableServices {

    @POST("table/getAllTables")
    Observable<BaseResponse<ArrayList<Table>>> getAllTables();

    @POST("table/openTable")
    Observable<BaseResponse<Boolean>> openTable(@Body OpenTableRequest req);
}
