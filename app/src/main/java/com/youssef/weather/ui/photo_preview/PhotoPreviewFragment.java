package com.youssef.weather.ui.photo_preview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youssef.weather.R;
import com.youssef.weather.adapters.PhotosAdapter;
import com.youssef.weather.util.Utilities;

import java.io.File;

import static com.youssef.weather.ui.photo_preview.PhotoPreviewActivity.FILE_KEY;


public class PhotoPreviewFragment extends Fragment {
    private File file;

    private ImageView imgFullView;

    private PhotoPreviewViewModel photoPreviewViewModel;

    public static PhotoPreviewFragment newInstance(File file) {
        PhotoPreviewFragment photoPreviewFragment = new PhotoPreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FILE_KEY, file);
        photoPreviewFragment.setArguments(bundle);
        return photoPreviewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_photo_preview, container, false);
        imgFullView = view.findViewById(R.id.imgFullView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photoPreviewViewModel = ViewModelProviders.of(this).get(PhotoPreviewViewModel.class);
        photoPreviewViewModel.setPhoto(getArguments());

        photoPreviewViewModel.getPhoto().observe(this, new Observer<File>() {
            @Override
            public void onChanged(@Nullable File file) {
                if (file != null) {
                    showPhoto(file);
                    PhotoPreviewFragment.this.file = file;
                }
            }
        });
    }

    private void showPhoto(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imgFullView.setImageBitmap(bitmap);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Utilities.shareFile(file, getContext());
                return true;
            default:
                break;
        }

        return false;
    }
}
