package com.kplar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kplar.models.topCategoriesPackage.TopCategorySlider;
import com.kplar.R;

import java.util.List;

public class TopCategoryAdapter extends RecyclerView.Adapter<TopCategoryAdapter.MyViewHolder> {

    List<TopCategorySlider> images;
    Context context;

    public TopCategoryAdapter(List<TopCategorySlider> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_category_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String imageUrl = images.get(position).getPicpath();
        holder.setImagefn(imageUrl);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myImage = itemView.findViewById(R.id.myImage);
        }

        public void setImagefn(String url) {

            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.category)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(myImage);


        }
    }


}
