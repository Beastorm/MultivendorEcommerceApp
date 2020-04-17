package com.kplar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kplar.models.offerPackage.Data;
import com.kplar.R;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {


    List<Data> offerList;
    Context context;

    public OfferAdapter(List<Data> offerList, Context context) {
        this.offerList = offerList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.offerItem.setText(offerList.get(position).getDetails());

    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView offerItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            offerItem = itemView.findViewById(R.id.offer_item);


        }
    }
}
