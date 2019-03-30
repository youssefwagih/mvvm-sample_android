package com.linkdev.practiseapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkdev.practiseapp.R;

import java.io.File;
import java.util.List;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    private List<File> filesList;
    private Context context;

    public PhotosAdapter(Context context, List<File> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_photo, parent, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        Bitmap bitmap = BitmapFactory.decodeFile(filesList.get(position).getAbsolutePath());
        holder.ivPicture.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }


    class PhotosViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        ImageView ivPicture;

        PhotosViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ivPicture = mView.findViewById(R.id.ivPicture);
        }
    }
}
