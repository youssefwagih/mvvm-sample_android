package com.youssef.weather.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by youssef on 5/20/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected TextView toolbarTitle;
    protected Toolbar toolbar;

    protected void setToolbar(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }
}
