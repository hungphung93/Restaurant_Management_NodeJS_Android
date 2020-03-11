package com.example.restaurantmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.AuthorizeUser;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.UserInfo;
import com.example.restaurantmanagement.Services.Implementation.AuthServices;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            String username = "Hanson";
            String password = "12345";

            ApiResponse<UserInfo> userInfo = AuthServices.Login(new AuthorizeUser(username, password));
            userInfo.Subscribe(this::handleLoginSuccess, this::handleLoginError);

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void handleLoginSuccess(BaseResponse<UserInfo> data){
        UserInfo userInfo = data.GetData();
        Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
    }

    private void handleLoginError(Throwable t){
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }
}
