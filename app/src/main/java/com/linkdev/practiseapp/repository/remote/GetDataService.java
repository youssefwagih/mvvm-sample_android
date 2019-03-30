package com.linkdev.practiseapp.repository.remote;

import com.linkdev.practiseapp.repository.model.Repo;
import com.linkdev.practiseapp.repository.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */


public interface GetDataService {
    @GET("/data/2.5/weather?APPID=3646d0447f17c43fe0d9caa38f6eb274&q=cairo&units=metric")
    Call<WeatherResponse> getCurrentWeatherData();
}