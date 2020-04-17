package com.kplar.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kplar.R;
import com.kplar.activities.BillDetailsActivity;
import com.kplar.adapters.CartAdapter;
import com.kplar.models.billAndProductPackage.BillProduct;
import com.kplar.models.cartPackage.Cart;
import com.kplar.models.cartPackage.Data;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;

import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment implements CartAdapter.CartCommunicator {

    ProgressBar progressBar;
    RecyclerView cartRecyclerView;
    List<Data> cartItems;
    ApiInterface apiInterface, removeToCartInterface;

    Context context;
    CartAdapter cartAdapter;
    ConstraintLayout emptyCartMsg;
    MenuItem menuItem;
    TextView totalCostTv;
    Button buyNow;
    ConstraintLayout constraintLayout;


    private String[] ids;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cart_recyclerView);
        emptyCartMsg = view.findViewById(R.id.empty_cart);

        totalCostTv = view.findViewById(R.id.total_cost_text_view);
        buyNow = view.findViewById(R.id.buy_now_btn);
        constraintLayout = view.findViewById(R.id.cart_cost_with_buy_btn);

        progressBar = view.findViewById(R.id.progressBar);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartRecyclerView.setHasFixedSize(true);


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillDetailsActivity.class);
                intent.putExtra("source", "cart");
                context.startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllCartItems(new MyPrefernces(context).readName());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cart");


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public void delToMyCart(String productId, String userName) {

        removeToCartInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call1 = removeToCartInterface.removeProductToCart(productId, userName);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Removed From Your Cart" + cartItems, Toast.LENGTH_SHORT).show();
                    Log.i("CartFragment", "xsxsxs" + cartItems);

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void removeAllCart(String userName) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call1 = apiInterface.removeAllCart(userName);
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


    public void getAllCartItems(final String userName) {

        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Cart> query = apiInterface.viewAllCart(userName);

        query.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {


                    if (response.body().getResponse().equals("ok")) {

                        emptyCartMsg.setVisibility(View.INVISIBLE);
                        cartRecyclerView.setVisibility(View.VISIBLE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        cartItems = response.body().getData();
                        cartAdapter = new CartAdapter(cartItems, context);
                        cartRecyclerView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                        getTotalCost(userName);

                        //menuItem.setEnabled(true);

                    } else {

                        emptyCartMsg.setVisibility(View.VISIBLE);
                        cartRecyclerView.setVisibility(View.INVISIBLE);
                        constraintLayout.setVisibility(View.INVISIBLE);
                        //menuItem.setEnabled(false);


                    }
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void updateProductQty(String productId, String userName, String qty) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> query = apiInterface.updateCart(productId, userName, qty);

        query.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onDetach() {
        super.onDetach();


    }


    public void getTotalCost(String userName) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<BillProduct> query = apiInterface.getProductWithBill(userName);

        query.enqueue(new Callback<BillProduct>() {
            @Override
            public void onResponse(Call<BillProduct> call, Response<BillProduct> response) {

                if (response.isSuccessful()) {


                    double totalAmount = response.body().getTotal();


                    Locale locale = new Locale("en", "IN");
                    totalCostTv.setText(String.format(locale, "%,.2f", totalAmount));


                }

            }

            @Override
            public void onFailure(Call<BillProduct> call, Throwable t) {

            }

        });


    }


    @Override
    public void delCartItem(String productId) {

    }

    @Override
    public void getAllProductIdWithTotalCost(String[] productIds, double totalCost) {

    }

    @Override
    public void getProductDetails(String productId, String qty) {

    }


}
