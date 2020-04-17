package com.kplar.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kplar.adapters.ProductsAdapter;
import com.kplar.models.productsPackage.Products;
import com.kplar.models.productsPackage.ProductsData;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    CardView sortBottomSheet;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<ProductsData> productsDataList;
    GridLayoutManager gridLayoutManager;
    ProductsAdapter productsAdapter;
    ApiInterface apiInterface;
    BottomSheetBehavior sheetBehavior;
    Button sheet_close_btn, priceFromL2H, priceFromH2L, newProducts, oldProducts;
    String categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        sortBottomSheet = findViewById(R.id.bottom_sheet);
        toolbar = findViewById(R.id.homeToolbar);
        recyclerView = findViewById(R.id.productList);
        priceFromL2H = findViewById(R.id.priceL2H);
        priceFromH2L = findViewById(R.id.priceH2L);
        newProducts = findViewById(R.id.newProducts);
        oldProducts = findViewById(R.id.oldProducts);

        categoryId = getIntent().getStringExtra("id");


        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        sheet_close_btn = findViewById(R.id.sheet_close_btn);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        sheetBehavior = BottomSheetBehavior.from(sortBottomSheet);


        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            toolbar.setTitle(getIntent().getStringExtra("categoryName"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white_color));
            toolbar.setTitleTextAppearance(this, R.style.myFont);
        }

        getProductListing();


        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
               /* switch (i) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }*/
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


        sheet_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


        // products listing from low price to high price
        priceFromL2H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sort ="0";
                getSortedProducts(sort);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });


        // products listing from hign price to low price
        priceFromH2L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sort ="1";
                getSortedProducts(sort);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        // products listing from new to old
        newProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sort ="2";
                getSortedProducts(sort);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        oldProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sort ="3";
                getSortedProducts(sort);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

    }

    private void getSortedProducts(String sort) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Products> query = apiInterface.getSortedProducts(sort, categoryId);
        query.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {

                    productsDataList = response.body().getData();
                    productsAdapter = new ProductsAdapter(productsDataList, ProductsActivity.this);
                    recyclerView.setAdapter(productsAdapter);
                    productsAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

                Toast.makeText(ProductsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getProductListing() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Products> query = apiInterface.getProducts(getIntent().getStringExtra("id"));
        query.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {

                    productsDataList = response.body().getData();
                    productsAdapter = new ProductsAdapter(productsDataList, ProductsActivity.this);
                    recyclerView.setAdapter(productsAdapter);
                    productsAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

                Toast.makeText(ProductsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.extra_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.sort:
                toggleBottomSheet();
                return true;

            case R.id.category:


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
