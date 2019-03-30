package com.linkdev.practiseapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.linkdev.practiseapp.R;
import com.linkdev.practiseapp.base.BaseActivity;
import com.linkdev.practiseapp.ui.weather.WeatherFragment;

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
