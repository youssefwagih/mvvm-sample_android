package com.linkdev.practiseapp.ui.photo_preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;

import com.linkdev.practiseapp.R;
import com.linkdev.practiseapp.base.BaseDetailsActivity;

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
        setToolbar(getString(R.string.photos));

        File file = null;
        if (getIntent().getExtras() != null)
            file = (File) getIntent().getExtras().get(FILE_KEY);

        getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, PhotoPreviewFragment.newInstance(file)).commit();
    }
}
