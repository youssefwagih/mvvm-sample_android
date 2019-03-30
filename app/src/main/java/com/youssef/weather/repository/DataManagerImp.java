package com.youssef.weather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.youssef.weather.BuildConfig;
import com.youssef.weather.network.RetrofitClientInstance;
import com.youssef.weather.repository.model.WeatherResponse;
import com.youssef.weather.repository.remote.GetDataService;
import com.youssef.weather.util.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public class DataManagerImp implements DataManager {
    private Context context;

    public DataManagerImp(Context context) {
        this.context = context;
    }

    @Override
    public List<File> getPhotos() {
        return Arrays.asList(FileHelper.getImagesInAppDirectory(context));
    }

    @Override
    public MutableLiveData<WeatherResponse> getCurrentWeatherData() {
        final MutableLiveData<WeatherResponse> weatherResponseMutableLiveData = new MutableLiveData<>();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(BuildConfig.API_KEY, "cairo", "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, retrofit2.Response<WeatherResponse> response) {
                weatherResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d("", "");
            }
        });

        return weatherResponseMutableLiveData;
    }

    @Override
    public void saveFile(Bitmap bitmap) {
        try {
            FileHelper.savebitmap(bitmap, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
