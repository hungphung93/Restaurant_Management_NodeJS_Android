package com.example.restaurantmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.AuthorizeUser;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.LoggingUser;
import com.example.restaurantmanagement.Models.UserInfo;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.AuthServices;

public class LoginActivity extends AppCompatActivity{

    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            Toast.makeText(getApplicationContext(),"RUNNING", Toast.LENGTH_SHORT).show();

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        try{
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            ApiResponse<UserInfo> userInfo = AuthServices.Login(new AuthorizeUser(username, password));
            userInfo.Subscribe(this::handleLoginSuccess, this::handleLoginError);
        }catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View view) {
        etUsername.setText("");
        etPassword.setText("");
    }

    private void handleLoginSuccess(BaseResponse<UserInfo> response){
        if(!response.IsSuccess())
        {
            Toast.makeText(getApplicationContext(),response.GetMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        UserInfo userInfo = response.GetData();
        LoggingUser.setUserInfo(userInfo);

        Intent intent = new Intent(this, MainActivity.class);
        //Intent intent = new Intent(this, TableOrderActivity.class);
        //Intent intent = new Intent(this, OrderListActivity.class);
        startActivity(intent);
    }

    private void handleLoginError(Throwable t){
        Toast.makeText(this, "Internal error happened. Please try later.",
                Toast.LENGTH_LONG).show();
    }
}
