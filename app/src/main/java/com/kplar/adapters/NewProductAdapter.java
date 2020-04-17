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
import com.kplar.models.newProductsPackage.Data;

import java.util.List;
import java.util.Locale;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewHolder> {

    List<Data> newProductList;
    Context context;

    public NewProductAdapter(List<Data> newProductList, Context context) {
        this.newProductList = newProductList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_products_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        double mrp, discountPer, discountPrice;
        mrp = Double.parseDouble(newProductList.get(position).getProductPrice());
        discountPer = Double.parseDouble(newProductList.get(position).getDiscount());
        discountPrice = mrp - ((discountPer / 100) * mrp);

        Locale locale = new Locale("en", "IN");

        holder.sellingPrice.setText(String.format(locale, "%,.2f", discountPrice));

        Glide.with(context)
                .load(newProductList.get(position).getProductImage())
                .centerInside()
                .into(holder.productImg);

    }

    @Override
    public int getItemCount() {
        return 4;
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
