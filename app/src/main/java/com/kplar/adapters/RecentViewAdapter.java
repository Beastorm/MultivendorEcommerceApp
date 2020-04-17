package com.kplar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kplar.R;
import com.kplar.models.recentViewPackage.Product;

import java.util.List;
import java.util.Locale;

public class RecentViewAdapter extends RecyclerView.Adapter<RecentViewAdapter.MyViewHolder> {

    List<Product> productList;
    Context context;

    public RecentViewAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_product_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        double mrp, discountPer, discountPrice;
        mrp = Double.parseDouble(productList.get(position).getProductPrice());
        discountPer = Double.parseDouble(productList.get(position).getDiscount());
        discountPrice = mrp - ((discountPer / 100) * mrp);

        Locale locale = new Locale("en", "IN");

        holder.sellingPrice.setText(String.format(locale, "%,.2f", discountPrice));

        Glide.with(context)
                .load(productList.get(position).getProductImage())
                .centerInside()
                .into(holder.productImg);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImg;
        TextView sellingPrice;
        Button buyNow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.product_img);
            sellingPrice = itemView.findViewById(R.id.selling_price);
            buyNow = itemView.findViewById(R.id.buy_now_btn);
        }
    }
}
