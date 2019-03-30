package com.youssef.weather.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://api.openweathermap.org/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}