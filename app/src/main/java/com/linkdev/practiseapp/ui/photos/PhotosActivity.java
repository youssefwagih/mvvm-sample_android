package com.linkdev.practiseapp.ui.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.linkdev.practiseapp.R;
import com.linkdev.practiseapp.base.BaseActivity;
import com.linkdev.practiseapp.base.BaseDetailsActivity;
import com.linkdev.practiseapp.ui.weather.WeatherFragment;

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
