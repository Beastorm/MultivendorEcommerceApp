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
import com.kplar.models.cartPackage.Data;
import com.kplar.R;

import java.util.List;

public class ProductForBillAdapter extends RecyclerView.Adapter<ProductForBillAdapter.MyViewHolder> {

    private List<Data> cartList;
    Context context;

    public ProductForBillAdapter(List<Data> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product_bill_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        double mrp, discountPer, discountPrice;
        mrp = Double.parseDouble(cartList.get(position).getProduct().getProductPrice());
        discountPer = Double.parseDouble(cartList.get(position).getProduct().getDiscount());
        discountPrice = mrp - ((discountPer / 100) * mrp);


        holder.productName.setText(cartList.get(position).getProduct().getProductName());
        holder.sellingPrice.setText(discountPrice + "");
        holder.mrp.setText(cartList.get(position).getProduct().getDiscount() + "% OFF");

        Glide.with(context)
                .load(cartList.get(position).getProduct().getProductImage())
                .centerInside()
                .into(holder.productImg);

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImg;
        TextView productName, sellingPrice, mrp, deliveryWithin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            sellingPrice = itemView.findViewById(R.id.selling_price);
            mrp = itemView.findViewById(R.id.mrp);
            deliveryWithin = itemView.findViewById(R.id.delivery_within);

        }
    }
}
