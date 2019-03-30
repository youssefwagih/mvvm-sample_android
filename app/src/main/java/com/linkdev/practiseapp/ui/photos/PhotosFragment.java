package com.linkdev.practiseapp.ui.photos;

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

import com.linkdev.practiseapp.R;
import com.linkdev.practiseapp.adapters.PhotosAdapter;

import java.io.File;
import java.util.List;


public class PhotosFragment extends Fragment {
    private PhotosAdapter adapter;
    private RecyclerView recyclerView;

    private PhotosViewModel photosViewModel;

    public PhotosFragment() {
        // Required empty public constructor
    }

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
            public void onChanged(@Nullable List<File> articlesResponse) {
                if (articlesResponse != null)
                    showArticlesList(articlesResponse);
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

    private void showArticlesList(List<File> filesList) {
        if (filesList != null && filesList.size() > 0) {
            adapter = new PhotosAdapter(getContext(), filesList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
