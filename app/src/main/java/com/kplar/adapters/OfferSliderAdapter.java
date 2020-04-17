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
import com.kplar.models.OfferSliders;
import com.kplar.R;

import java.util.List;

public class OfferSliderAdapter extends PagerAdapter {

    private List<OfferSliders> images;
    private Context context;
    private LayoutInflater layoutInflater;

    public OfferSliderAdapter(List<OfferSliders> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view ==(ConstraintLayout)object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_offer_slider_layout, container, false);
        ImageView imageView = view.findViewById(R.id.slideImage);

        String mySliderUrl = images.get(position).getPicPath();

        setImageSlide(imageView, mySliderUrl);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }


    public void setImageSlide(ImageView imageView, String url) {

        Glide.with(context).load(url).into(imageView);


    }
}
