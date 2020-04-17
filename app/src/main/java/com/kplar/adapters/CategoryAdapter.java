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
import com.kplar.activities.ProductsActivity;
import com.kplar.activities.SubCategoryActivity;
import com.kplar.models.categoriesPackage.CategoryData;
import com.kplar.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {


    private List<CategoryData> mList;
    private Context context;

    public CategoryAdapter(List<CategoryData> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String iconUrl = mList.get(position).getImage();
        String categoryName = mList.get(position).getCategoryname();
        holder.name.setText(categoryName);
        Glide.with(context)
                .load(iconUrl)
                .placeholder(R.drawable.category)
                .fitCenter()
                .into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon, frdBtn;
        TextView name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            icon = itemView.findViewById(R.id.categoryIcon);
            name = itemView.findViewById(R.id.categoryName);
            frdBtn = itemView.findViewById(R.id.forwardIcon);


        }

        @Override
        public void onClick(View v) {


            int pos = getAdapterPosition();
            String contentType = mList.get(pos).getContent();
            if (contentType.equals("s")) {
                Intent intent = new Intent(context, SubCategoryActivity.class);
                intent.putExtra("categoryName", mList.get(pos).getCategoryname());
                intent.putExtra("id", mList.get(pos).getId());
                context.startActivity(intent);

            } else if (contentType.equals("p")) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("categoryName", mList.get(pos).getCategoryname());
                intent.putExtra("id", mList.get(pos).getId());
                context.startActivity(intent);
            }


        }
    }
}
