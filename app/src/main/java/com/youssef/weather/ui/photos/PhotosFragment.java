package com.youssef.weather.ui.photos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youssef.weather.R;
import com.youssef.weather.adapters.PhotosAdapter;
import com.youssef.weather.ui.photo_preview.PhotoPreviewActivity;

import java.io.File;
import java.util.List;


public class PhotosFragment extends Fragment {
    private PhotosAdapter adapter;
    private RecyclerView recyclerView;

    private PhotosViewModel photosViewModel;

    public static PhotosFragment newInstance() {
        return new PhotosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        recyclerView = view.findViewById(R.id.rvItems);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);

        photosViewModel.getPhotos().observe(this, new Observer<List<File>>() {
            @Override
            public void onChanged(@Nullable List<File> filesList) {
                if (filesList != null)
                    showPhotos(filesList);
            }
        });

        photosViewModel.getError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean error) {
                if (error == null || error)
                    Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPhotos(List<File> filesList) {
        if (filesList != null && filesList.size() > 0) {
            adapter = new PhotosAdapter(getContext(), filesList, onDataItemClickListener);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    PhotosAdapter.OnDataItemClickListener onDataItemClickListener = new PhotosAdapter.OnDataItemClickListener() {
        @Override
        public void OnDataItemClick(File item) {
            PhotoPreviewActivity.startActivity(getContext(), item);
        }
    };
}
