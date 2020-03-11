package com.example.restaurantmanagement.Services.Interface;


import com.example.restaurantmanagement.Models.AuthorizeUser;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthServices {
    @POST("auth/login")
    Observable<BaseResponse<UserInfo>> Login(@Body AuthorizeUser user);
}
