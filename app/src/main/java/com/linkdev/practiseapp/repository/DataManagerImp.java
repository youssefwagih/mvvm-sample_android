package com.linkdev.practiseapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.linkdev.practiseapp.BuildConfig;
import com.linkdev.practiseapp.network.RetrofitClientInstance;
import com.linkdev.practiseapp.repository.model.WeatherResponse;
import com.linkdev.practiseapp.repository.remote.GetDataService;
import com.linkdev.practiseapp.util.FileHelper;

import java.io.File;
import java.util.ArrayList;
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
}
