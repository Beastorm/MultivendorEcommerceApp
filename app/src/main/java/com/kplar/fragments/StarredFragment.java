package com.kplar.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kplar.adapters.WishListAdapter;
import com.kplar.models.wishListPackage.Data;
import com.kplar.models.wishListPackage.WishListItem;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StarredFragment extends Fragment {

    RecyclerView wishListRecyclerView;
    List<Data> wishListItems;
    ApiInterface apiInterface, removeToWishListInterface;

    Context context;
    WishListAdapter wishListAdapter;
    TextView emptyWishListMsg;
    MenuItem menuItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_starred, container, false);

        emptyWishListMsg = view.findViewById(R.id.empty_wish_list_txt);

        wishListRecyclerView = view.findViewById(R.id.wishList);
        wishListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        wishListRecyclerView.setHasFixedSize(true);


        getAllWishList(new MyPrefernces(context).readName());


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void getAllWishList(String userName) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<WishListItem> query = apiInterface.viewWishList(userName);
        query.enqueue(new Callback<WishListItem>() {
            @Override
            public void onResponse(Call<WishListItem> call, Response<WishListItem> response) {

                if (response.isSuccessful()) {

                    if (response.body().getResponse().equals("ok")) {

                        emptyWishListMsg.setVisibility(View.INVISIBLE);
                        wishListRecyclerView.setVisibility(View.VISIBLE);
                        wishListItems = response.body().getData();
                        wishListAdapter = new WishListAdapter(wishListItems, context);
                        wishListRecyclerView.setAdapter(wishListAdapter);
                        wishListAdapter.notifyDataSetChanged();

                        //menuItem.setEnabled(true);

                    } else {

                        emptyWishListMsg.setVisibility(View.VISIBLE);
                        wishListRecyclerView.setVisibility(View.INVISIBLE);
                        //menuItem.setEnabled(false);


                    }

                }

            }

            @Override
            public void onFailure(Call<WishListItem> call, Throwable t) {

                Toast.makeText(context, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.i("wishlisterser", "mesaage   " + t.getMessage());

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void delToMyWishList(String productId, String userName) {

        removeToWishListInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call1 = removeToWishListInterface.removeProductToWishList(productId, userName);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Removed From Your WishList", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void removeAllWishList(String userName) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call1 = apiInterface.removeAllWishList(userName);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


    public void  getMenuItem(MenuItem item){
        menuItem =item;
    }
}
