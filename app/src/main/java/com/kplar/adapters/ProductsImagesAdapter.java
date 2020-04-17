package com.kplar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.kplar.models.productDetailsPackage.ImageSet;
import com.kplar.R;

import java.util.List;

public class ProductsImagesAdapter extends PagerAdapter {

    private List<ImageSet> productImages;
    private Context context;
    private LayoutInflater layoutInflater;

    public ProductsImagesAdapter(List<ImageSet> productImages, Context context) {
        this.productImages = productImages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_product_images_layout, container, false);
        ImageView imageView = view.findViewById(R.id.productImgId);
        String url = productImages.get(position).getPicPath();
        setImageSlide(imageView, url);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    private void setImageSlide(ImageView imageView, String url) {

        Glide.with(context).load(url).into(imageView);

    }
}
