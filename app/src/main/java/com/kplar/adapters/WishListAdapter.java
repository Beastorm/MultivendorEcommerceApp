package com.kplar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kplar.activities.ProductDetailsActivity;
import com.kplar.models.wishListPackage.Data;
import com.kplar.R;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    List<Data> allWishList;
    Context context;
    WishListCommunicator wishListCommunicator;

    public WishListAdapter(List<Data> allWishList, Context context) {
        this.allWishList = allWishList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_starred_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.productName.setText(allWishList.get(position).getProductName());
        holder.brand.setText(allWishList.get(position).getBrand());
        holder.stockStatus.setText("Stock " + allWishList.get(position).getStock());
        holder.sellingPrice.setText(allWishList.get(position).getProductPrice());
        holder.mrp.setText(allWishList.get(position).getDiscount() + "% OFF");
        Glide.with(context)
                .load(allWishList.get(position).getProductImage())
                .centerInside()
                .into(holder.productImg);

    }

    @Override
    public int getItemCount() {
        return allWishList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImg, delete;
        TextView productName, brand, stockStatus, sellingPrice, mrp, deliveryWithin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);


            productImg = itemView.findViewById(R.id.product_image);
            delete = itemView.findViewById(R.id.delete);
            productName = itemView.findViewById(R.id.product_name);
            brand = itemView.findViewById(R.id.brand_name);
            stockStatus = itemView.findViewById(R.id.stock_status);
            sellingPrice = itemView.findViewById(R.id.selling_price);
            mrp = itemView.findViewById(R.id.mrp);
            deliveryWithin = itemView.findViewById(R.id.delivery_within);

            delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.wish_list_item) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productId", allWishList.get(getAdapterPosition()).getProductId());
                intent.putExtra("productName", allWishList.get(getAdapterPosition()).getProductName());
                context.startActivity(intent);
            } else if (v.getId() == R.id.delete) {

                wishListCommunicator = (WishListCommunicator) context;
                wishListCommunicator.delWishListItem(allWishList.get(getAdapterPosition()).getProductId());
                /*allWishList.remove(getAdapterPosition());
                notifyDataSetChanged();*/


            }

        }
    }


    public interface WishListCommunicator {

        public void delWishListItem(String productId);

        //public void getMyWishListItems(String[] productIds);


    }






}
