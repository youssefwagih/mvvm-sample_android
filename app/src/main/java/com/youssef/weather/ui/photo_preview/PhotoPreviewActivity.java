package com.youssef.weather.ui.photo_preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.youssef.weather.R;
import com.youssef.weather.base.BaseDetailsActivity;

import java.io.File;

public class PhotoPreviewActivity extends BaseDetailsActivity {

    public static final String FILE_KEY = "FILE_KEY";

    public static void startActivity(Context context, File file) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        intent.putExtra(FILE_KEY, file);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setToolbar(getString(R.string.photo_preview));

        File file = null;
        if (getIntent().getExtras() != null)
            file = (File) getIntent().getExtras().get(FILE_KEY);

        getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, PhotoPreviewFragment.newInstance(file)).commit();
    }
}
