package com.youssef.weather.ui.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.youssef.weather.R;
import com.youssef.weather.base.BaseDetailsActivity;

public class PhotosActivity extends BaseDetailsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setToolbar(getString(R.string.photos));

        getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, PhotosFragment.newInstance()).commit();
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, PhotosActivity.class);
    }
}
