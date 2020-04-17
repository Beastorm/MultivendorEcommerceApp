package com.kplar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kplar.models.productDetailsPackage.Review;
import com.kplar.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    private List<Review> reviewList;
    private Context context;

    public ReviewsAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(reviewList.get(position).getReviewList().get(position).getUserName());
        holder.reviewMsg.setText(reviewList.get(position).getReviewList().get(position).getReview());
        holder.ratting.setRating(Float.valueOf(reviewList.get(position).getReviewList().get(position).getRate()));

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name, reviewMsg;
        RatingBar ratting;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.username);
            reviewMsg = itemView.findViewById(R.id.review);
            ratting = itemView.findViewById(R.id.rating);

        }
    }
}
