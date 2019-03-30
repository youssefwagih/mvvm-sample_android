package com.linkdev.practiseapp.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by peter on 10/11/18.
 */

public class FileHelper {


    public static File[] getImagesInAppDirectory(Context context) {
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM) ;
        if (dir != null)
            return dir.listFiles();
        else
            return null;
    }
}
