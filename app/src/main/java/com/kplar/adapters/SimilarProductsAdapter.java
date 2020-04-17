package com.kplar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kplar.models.productDetailsPackage.ProductList;
import com.kplar.R;

import java.util.List;

public class SimilarProductsAdapter extends RecyclerView.Adapter<SimilarProductsAdapter.MyViewHolder> {

    List<ProductList> productLists;
    Context context;
    OnSimilarProductListener onSimilarProductListener;

    public SimilarProductsAdapter(List<ProductList> productLists, Context context) {
        this.productLists = productLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar_products_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        double mrp, discountPer, discountPrice;
        mrp = Double.parseDouble(productLists.get(position).getProductPrice());
        discountPer = Double.parseDouble(productLists.get(position).getDiscount());
        discountPrice = mrp - ((discountPer / 100) * mrp);


        holder.productName.setText(productLists.get(position).getProductName());
        holder.brand.setText(productLists.get(position).getBrand());
        holder.discountPrice.setText(discountPrice + "");

        Glide.with(context).load(productLists.get(position).getProductImage()).into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productName, brand, discountPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            brand = itemView.findViewById(R.id.brand);
            discountPrice = itemView.findViewById(R.id.discount_price);
        }

        @Override
        public void onClick(View v) {
            onSimilarProductListener = (OnSimilarProductListener) context;
            onSimilarProductListener.fetchSimilarProductData(productLists.get(getAdapterPosition()).getProductId(),
                    productLists.get(getAdapterPosition()).getProductName());
        }
    }


    public interface OnSimilarProductListener {

        public void fetchSimilarProductData(String productId, String productName);

    }


}
