package com.kplar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kplar.adapters.SubCategoryAdapter;
import com.kplar.models.subcategoriesPackage.SubCategory;
import com.kplar.models.subcategoriesPackage.SubCategoryData;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    SubCategoryAdapter subCategoryAdapter;
    List<SubCategoryData> subCategoryDatas;
    GridLayoutManager gridLayoutManager;
    ApiInterface apiInterface;
    String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        recyclerView = findViewById(R.id.subCategoriesId);
        toolbar = findViewById(R.id.homeToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        catId = getIntent().getStringExtra("id");



        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle(getIntent().getStringExtra("categoryName"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_color));
        toolbar.setTitleTextAppearance(this, R.style.myFont);


        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        getSubCategoriesData();
    }

    private void getSubCategoriesData() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SubCategory> query = apiInterface.getSubCategories(catId);

        query.enqueue(new Callback<SubCategory>() {
            @Override
            public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {

                if (response.isSuccessful()) {

                    subCategoryDatas = response.body().getData();
                    subCategoryAdapter = new SubCategoryAdapter(subCategoryDatas, SubCategoryActivity.this);
                    recyclerView.setAdapter(subCategoryAdapter);
                    subCategoryAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onFailure(Call<SubCategory> call, Throwable t) {
                Toast.makeText(SubCategoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.home:

                return true;

            case R.id.category:


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
