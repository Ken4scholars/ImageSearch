package com.verticals.imagesearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kenneth on 25.07.17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder>{

    private List<String> photos;

    public PhotoAdapter(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picture_recycler_item, parent, false);
        return new PhotoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        holder.bindphoto(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void fillRecycler(List<String> photos){
        for (String photo: photos) {
            this.photos.add(photo);
            notifyItemInserted(photos.size()-1);
        }
//        notifyDataSetChanged();
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private String photoUrl;

        public PhotoHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.image);
        }

        public void bindphoto(String photoUrl){
            this.photoUrl = photoUrl;
            Picasso.with(photo.getContext())
                    .load(photoUrl)
                    .resize(400, 500) // resizes the image to these dimensions (in pixel)
                    .centerCrop()
                    .into(photo);
        }
    }
}
