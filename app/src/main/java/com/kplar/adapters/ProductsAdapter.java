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
import com.kplar.models.productsPackage.ProductsData;
import com.kplar.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private List<ProductsData> productsDataList;
    private Context context;

    public ProductsAdapter(List<ProductsData> productsDataList, Context context) {
        this.productsDataList = productsDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products_layout, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        double mrp, discountPer, discountPrice;
        mrp = Double.parseDouble(productsDataList.get(position).getProductPrice());
        discountPer = Double.parseDouble(productsDataList.get(position).getDiscount());

        discountPrice = mrp - ((discountPer / 100) * mrp);

        Glide.with(context).load(productsDataList.get(position).getProductImage()).into(holder.proImg);
        holder.productName.setText(productsDataList.get(position).getProductName());
        holder.proDesc.setText(productsDataList.get(position).getProductDescripition());
        holder.mrp.setText(productsDataList.get(position).getProductPrice());
        holder.productDiscountPercent.setText(productsDataList.get(position).getDiscount() + "% OFF");

        holder.proDiscountPrice.setText(discountPrice+"");


    }

    @Override
    public int getItemCount() {
        return productsDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView starred, proImg;
        TextView productName, proDesc, mrp, proDiscountPrice, productDiscountPercent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            starred = itemView.findViewById(R.id.starred_imgId);
            proImg = itemView.findViewById(R.id.product_imgId);
            productName = itemView.findViewById(R.id.productName);
            proDesc = itemView.findViewById(R.id.productDescId);
            proDiscountPrice = itemView.findViewById(R.id.product_dicountPriceId);
            mrp = itemView.findViewById(R.id.mrpId);
            productDiscountPercent = itemView.findViewById(R.id.discountPercentId);


        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productId", productsDataList.get(getAdapterPosition()).getProductId());
            intent.putExtra("productName", productsDataList.get(getAdapterPosition()).getProductName());
            context.startActivity(intent);

        }
    }
}
