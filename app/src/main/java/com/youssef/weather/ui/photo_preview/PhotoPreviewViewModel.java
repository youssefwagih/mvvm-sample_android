package com.youssef.weather.ui.photo_preview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;

import java.io.File;

import static com.youssef.weather.ui.photo_preview.PhotoPreviewActivity.FILE_KEY;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public class PhotoPreviewViewModel extends AndroidViewModel {

    public MutableLiveData<File> fileMutableLiveData = new MutableLiveData<>();

    public PhotoPreviewViewModel(Application application) {
        super(application);
    }

    public LiveData<File> getPhoto() {
        return fileMutableLiveData;
    }

    public void setPhoto(Bundle arguments) {
        File file = (File) arguments.get(FILE_KEY);
        fileMutableLiveData.setValue(file);
    }
}
