package com.linkdev.practiseapp.repository.remote;

import com.linkdev.practiseapp.repository.model.Repo;
import com.linkdev.practiseapp.repository.model.WeatherResponse;

import java.util.List;

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