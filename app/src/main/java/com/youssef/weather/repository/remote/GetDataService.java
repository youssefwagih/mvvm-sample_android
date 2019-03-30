package com.youssef.weather.repository.remote;

import com.youssef.weather.repository.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */


public interface GetDataService {
    @GET("/data/2.5/weather")
    Call<WeatherResponse> getCurrentWeatherData(@Query("APPID") String apiKey, @Query("q") String city, @Query("units") String unit);
}