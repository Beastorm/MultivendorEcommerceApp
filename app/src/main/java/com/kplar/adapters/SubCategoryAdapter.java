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
import com.kplar.models.subcategoriesPackage.SubCategoryData;
import com.kplar.R;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    List<SubCategoryData> subCategoryDataList;
    Context context;

    public SubCategoryAdapter(List<SubCategoryData> subCategoryDataList, Context context) {
        this.subCategoryDataList = subCategoryDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(subCategoryDataList.get(position).getName());
        Glide.with(context).load(subCategoryDataList.get(position).getImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return subCategoryDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.subCatImgId);
            name = itemView.findViewById(R.id.subcatnameId);
        }
    }
}
