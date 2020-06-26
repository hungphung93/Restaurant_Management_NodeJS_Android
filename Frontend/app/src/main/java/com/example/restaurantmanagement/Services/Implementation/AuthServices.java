package com.example.restaurantmanagement.Services.Implementation;

import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.AuthorizeUser;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.UserInfo;
import com.example.restaurantmanagement.Services.Interface.IAuthServices;
import com.example.restaurantmanagement.Utilities.HttpHelper;
import io.reactivex.Observable;

public class AuthServices {
    public static ApiResponse<UserInfo> Login(AuthorizeUser user)throws Exception{
        try{
            IAuthServices authServices = HttpHelper.CreateApiService(IAuthServices.class);

            return new ApiResponse(authServices.Login(user));

        }catch(Exception ex){
            throw ex;
        }
    }
}
