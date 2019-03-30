package com.youssef.weather.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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

    public static File savebitmap(Bitmap bmp, Context context) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        Date currentTime = Calendar.getInstance().getTime();

        File mydirf = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if (!mydirf.exists())
            mydirf.mkdir();

        File f = new File(mydirf, currentTime.toString() + ".jpg");

        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }
}

