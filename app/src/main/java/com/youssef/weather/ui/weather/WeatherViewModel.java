package com.youssef.weather.ui.weather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.youssef.weather.repository.DataManager;
import com.youssef.weather.repository.DataManagerImp;
import com.youssef.weather.repository.model.WeatherResponse;

import java.io.IOException;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

 public class WeatherViewModel extends AndroidViewModel {
    private DataManager dataManager;
    public MutableLiveData<Boolean> saveMutableLiveData = new MutableLiveData<>();

    public WeatherViewModel(Application application) {
        super(application);
        dataManager = new DataManagerImp(application.getApplicationContext());
    }

    MutableLiveData<WeatherResponse> getCurrentWeatherData() {
        return  dataManager.getCurrentWeatherData();
    }

    void savePhoto(Bitmap bitmap) throws IOException {
        dataManager.saveFile(bitmap);
        saveMutableLiveData.setValue(true);
    }

    public LiveData<Boolean> getFileSaveStatus() {
        return saveMutableLiveData;
    }
}
