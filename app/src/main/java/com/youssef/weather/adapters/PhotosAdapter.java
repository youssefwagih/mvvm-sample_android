package com.youssef.weather.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youssef.weather.R;

import java.io.File;
import java.util.List;

/**
 * Created by Youssef.Waguih on 9/24/2018.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    private List<File> filesList;
    private Context context;
    private final OnDataItemClickListener mListener;

    public PhotosAdapter(Context context, List<File> filesList, OnDataItemClickListener listener) {
        this.context = context;
        this.filesList = filesList;
        mListener = listener;
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

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            if (null != mListener) {
                mListener.OnDataItemClick(filesList.get(pos));
            }
        }
    };


    class PhotosViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        ImageView ivPicture;

        PhotosViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ivPicture = mView.findViewById(R.id.ivPicture);
        }
    }

    public interface OnDataItemClickListener {
        void OnDataItemClick(File item);
    }
}
