package com.youssef.weather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.youssef.weather.repository.model.WeatherResponse;

import java.io.File;
import java.util.List;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public interface DataManager {
    List<File> getPhotos();
    MutableLiveData<WeatherResponse> getCurrentWeatherData();
    void saveFile(Bitmap bitmap);
}
