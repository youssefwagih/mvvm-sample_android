package com.youssef.weather.ui;

import android.os.Bundle;

import com.youssef.weather.R;
import com.youssef.weather.base.BaseActivity;
import com.youssef.weather.ui.weather.WeatherFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setToolbar(getString(R.string.home));

        getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, WeatherFragment.newInstance()).commit();
    }
}
