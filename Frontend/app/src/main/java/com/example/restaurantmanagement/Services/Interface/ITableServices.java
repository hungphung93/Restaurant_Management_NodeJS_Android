package com.example.restaurantmanagement.Services.Interface;

import com.example.restaurantmanagement.Models.AddOrderToTableRequest;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.ChangeStatusOfOrderRequest;
import com.example.restaurantmanagement.Models.GetOrderedFoodRequest;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.Models.TableTransactionDetail;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ITableServices {

    @POST("table/getAllTables")
    Observable<BaseResponse<ArrayList<Table>>> getAllTables();

    @POST("table/openTable")
    Observable<BaseResponse<Boolean>> openTable(@Body OpenTableRequest req);

    @POST("table/closeTable")
    Observable<BaseResponse<Boolean>> closeTable(@Body OpenTableRequest req);

    @POST("table/getTableDetail")
    Observable<BaseResponse<TableTransactionDetail>> getTableDetail(@Body OpenTableRequest req);

    @POST("table/getAllOrderedFoodByRole")
    Observable<BaseResponse<ArrayList<Order>>> getAllOrderedFoodByRole(@Body GetOrderedFoodRequest req);

    @POST("/table/addFoodsToTable")
    Observable<BaseResponse<Boolean>> addFoodsToTable(@Body AddOrderToTableRequest req);

    @POST("/table/changeStatusOfOrder")
    Observable<BaseResponse<Boolean>> changeStatusOfOrder(@Body ChangeStatusOfOrderRequest req);

    @POST("table/getOrderSummary")
    Observable<BaseResponse<TableTransactionDetail>> getOrderSummary(@Body OpenTableRequest req);

}
