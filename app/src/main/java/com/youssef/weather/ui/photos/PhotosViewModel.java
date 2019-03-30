package com.youssef.weather.ui.photos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.youssef.weather.repository.DataManager;
import com.youssef.weather.repository.DataManagerImp;

import java.io.File;
import java.util.List;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public class PhotosViewModel extends AndroidViewModel {
    private DataManager dataManager;

    public MutableLiveData<List<File>> filesResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorMutableLiveData = new MutableLiveData<>();

    public PhotosViewModel(Application application) {
        super(application);
        dataManager = new DataManagerImp(application.getApplicationContext());
        fetchArticlesItems();
    }

    private void fetchArticlesItems() {
        List<File> fileList = dataManager.getPhotos();

        if (fileList != null && fileList.size() > 0) {
            filesResponseMutableLiveData.setValue(fileList);
            errorMutableLiveData.setValue(false);
        } else {
            errorMutableLiveData.setValue(true);
        }
    }

    LiveData<List<File>> getPhotos() {
        return filesResponseMutableLiveData;
    }

    LiveData<Boolean> getError() {
        return errorMutableLiveData;
    }
}
