package com.example.lofm.githubfollowers.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.lofm.githubfollowers.R;
import com.example.lofm.githubfollowers.model.GHUser;
import com.example.lofm.githubfollowers.rest.VolleySingleton;
import com.example.lofm.githubfollowers.ui.ImageListener;

import java.util.List;

/**
 * Created by LOFM on 22/02/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ImageViewHolder> {

    private Context context;
    private ImageListener imageListener;
    private List<GHUser> ghUsers;
    private ImageLoader imageLoader;

    public GridAdapter(Context context, ImageListener imageListener) {
        this.context = context;
        this.imageListener = imageListener;
        imageLoader = VolleySingleton.getInstance(context).getImageLoader();
    }

    public void setGHUsers(List<GHUser> ghUsers) {
        this.ghUsers = ghUsers;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        holder.initialImage.setImageUrl(ghUsers.get(position).getAvatar_url(), imageLoader);
        ViewCompat.setTransitionName(holder.initialImage, String.valueOf(position) + "_image");
        holder.initialImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageListener.onImageClicked(holder.initialImage, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ghUsers.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        protected NetworkImageView initialImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            initialImage = (NetworkImageView) itemView.findViewById(R.id.initialImage);
        }
    }
}
