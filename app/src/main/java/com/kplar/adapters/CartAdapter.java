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
import com.kplar.models.cartPackage.Data;
import com.kplar.R;

import java.util.List;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Data> cartList;
    Context context;
    private CartCommunicator cartCommunicator;


    public CartAdapter(List<Data> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        double mrp, discountPer, discountPrice;
        mrp = Double.parseDouble(cartList.get(position).getProduct().getProductPrice());
        discountPer = Double.parseDouble(cartList.get(position).getProduct().getDiscount());
        discountPrice = mrp - ((discountPer / 100) * mrp);


        holder.productName.setText(cartList.get(position).getProduct().getProductName());
        holder.brand.setText(cartList.get(position).getProduct().getBrand());
        holder.stockStatus.setText("Stock " + cartList.get(position).getProduct().getStock());
        holder.sellingPrice.setText(discountPrice + "");
        holder.mrp.setText(cartList.get(position).getProduct().getDiscount() + "% OFF");
        holder.stepperTouch.setCount(Integer.parseInt(cartList.get(position).getProduct().getQuantity()));
        Glide.with(context)
                .load(cartList.get(position).getProduct().getProductImage())
                .centerInside()
                .into(holder.productImg);

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImg, delete;
        TextView productName, brand, stockStatus, sellingPrice, mrp, deliveryWithin;
        StepperTouch stepperTouch;

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
            stepperTouch = itemView.findViewById(R.id.stepperTouch);

            delete.setOnClickListener(this);


            stepperTouch.setMinValue(1);
            stepperTouch.setMaxValue(10);
            stepperTouch.setSideTapEnabled(true);


            stepperTouch.addStepCallback(new OnStepCallback() {
                @Override
                public void onStep(int i, boolean b) {
                    cartCommunicator = (CartCommunicator) context;
                    cartCommunicator.getProductDetails(cartList.get(getAdapterPosition()).getProduct().getProductId(), i + "");

                }
            });


        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cart_items) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productId", cartList.get(getAdapterPosition()).getProduct().getProductId());
                intent.putExtra("productName", cartList.get(getAdapterPosition()).getProduct().getProductName());
                context.startActivity(intent);
            } else if (v.getId() == R.id.delete) {

                cartCommunicator = (CartCommunicator) context;
                cartCommunicator.delCartItem(cartList.get(getAdapterPosition()).getProduct().getProductId());
                notifyItemRemoved(getAdapterPosition());


            }
        }
    }


    public interface CartCommunicator {

        public void delCartItem(String productId);

        public void getAllProductIdWithTotalCost(String[] productIds, double totalCost);

        public void getProductDetails(String productId, String qty);

    }
}
