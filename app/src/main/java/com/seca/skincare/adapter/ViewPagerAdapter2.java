package com.seca.skincare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seca.skincare.R;

public class ViewPagerAdapter2 extends RecyclerView.Adapter<ViewPagerAdapter2.ViewHolder> {

    // Array of images
    // Adding images from drawable folder
    private int[] images = {R.drawable.onboard1, R.drawable.onboard2, R.drawable.onboard3,R.drawable.onboard4};
    private Context ctx;

    // Constructor of our ViewPager2Adapter class
    public ViewPagerAdapter2(Context ctx) {
        this.ctx = ctx;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.welcome_item_vieew, parent, false);
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
     //   holder.images.setBackgroundResource(images[position]);
        holder.images.setBackgroundResource(images[position]);
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return images.length;
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.images);
        }
    }
}