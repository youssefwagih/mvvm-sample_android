package com.youssef.weather.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;

public class Utilities {
    public static void shareFile(File file, Context context) {
        Intent sharingIntent = new Intent("android.intent.action.SEND");
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getMimeTypeFromExtension(ext);
        sharingIntent.setType(type);
        sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
