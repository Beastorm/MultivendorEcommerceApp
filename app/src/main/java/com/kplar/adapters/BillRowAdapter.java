package com.kplar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kplar.models.billAndProductPackage.Data;
import com.kplar.R;

import java.util.List;

public class BillRowAdapter extends RecyclerView.Adapter<BillRowAdapter.MyViewHolder> {

    List<Data> billList;
    Context context;

    public BillRowAdapter(List<Data> billList, Context context) {
        this.billList = billList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_row_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.productName.setText(billList.get(position).getProductname());
        holder.qty.setText(billList.get(position).getQuantity());
        holder.mrp.setText(billList.get(position).getMrp());
        holder.off.setText(billList.get(position).getDiscount());
        holder.shippingCharge.setText(billList.get(position).getShippingcharge() + "");
        holder.coupon.setText(billList.get(position).getCoupon() + "");
        holder.subTotal.setText(billList.get(position).getSubtotal() + "");
    }


    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, qty, mrp, off, shippingCharge, coupon, subTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
            qty = itemView.findViewById(R.id.qty);
            mrp = itemView.findViewById(R.id.mrp);
            off = itemView.findViewById(R.id.off);
            shippingCharge = itemView.findViewById(R.id.sc);
            coupon = itemView.findViewById(R.id.coupon);
            subTotal = itemView.findViewById(R.id.sub_total);

        }
    }
}
