package com.linkdev.practiseapp.base;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by youssef on 5/20/18.
 */

public abstract class BaseDetailsActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void setToolbar(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
