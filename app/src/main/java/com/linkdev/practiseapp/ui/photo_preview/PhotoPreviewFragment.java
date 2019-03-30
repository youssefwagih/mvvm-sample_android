package com.linkdev.practiseapp.ui.photo_preview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.linkdev.practiseapp.R;
import com.linkdev.practiseapp.adapters.PhotosAdapter;

import java.io.File;
import java.util.List;

import static com.linkdev.practiseapp.ui.photo_preview.PhotoPreviewActivity.FILE_KEY;


public class PhotoPreviewFragment extends Fragment {
    private PhotosAdapter adapter;
    private ImageView imgFullView;

    private PhotoPreviewViewModel photoPreviewViewModel;

    public PhotoPreviewFragment() {
        // Required empty public constructor
    }

    public static PhotoPreviewFragment newInstance(File file) {
        PhotoPreviewFragment photoPreviewFragment = new PhotoPreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FILE_KEY, file);
        photoPreviewFragment.setArguments(bundle);
        return photoPreviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
            public void onChanged(@Nullable File articlesResponse) {
                if (articlesResponse != null)
                    showPhoto(articlesResponse);
            }
        });
    }

    private void showPhoto(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imgFullView.setImageBitmap(bitmap);
    }
}
