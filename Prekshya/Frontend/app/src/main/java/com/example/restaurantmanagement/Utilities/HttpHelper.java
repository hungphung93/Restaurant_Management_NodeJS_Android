package com.example.restaurantmanagement.Utilities;

import android.os.AsyncTask;

import com.example.restaurantmanagement.Models.UserInfo;
import com.example.restaurantmanagement.Services.Interface.IAuthServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper{
    // Trailing slash is needed
    private static final String BASE_URL = "http://192.168.0.21:3000/";
    private static Retrofit instance = null;



    public static Retrofit GetInstance(){
        if(instance == null){
            /*
            // Http Interceptors to add JWT Authentication
            OkHttpClient client = new OkHttpClient();

            client.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                                            .addHeader("Authorization", "JWT")
                                            .build();

                    return chain.proceed(chain.request());
                }
            });
            */
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //        .client(client)
                    .build();
        }

        return instance;
    }

    public static <T> T CreateApiService(final Class<T> apiService){

        return GetInstance().create(apiService);
    }
}
