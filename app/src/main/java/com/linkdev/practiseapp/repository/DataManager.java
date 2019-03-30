package com.linkdev.practiseapp.repository;

import android.arch.lifecycle.MutableLiveData;

import com.linkdev.practiseapp.repository.model.Repo;
import com.linkdev.practiseapp.repository.model.WeatherResponse;

import java.io.File;
import java.util.List;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public interface DataManager {
    List<File> getPhotos();
    MutableLiveData<WeatherResponse> getCurrentWeatherData();
}
