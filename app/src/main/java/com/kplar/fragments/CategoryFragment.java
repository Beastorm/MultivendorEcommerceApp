package com.kplar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kplar.adapters.CategoryAdapter;
import com.kplar.adapters.DealsAdapter;
import com.kplar.models.categoriesPackage.Category;
import com.kplar.models.categoriesPackage.CategoryData;
import com.kplar.models.DealSlider;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {

    ViewPager myViewPager;
    List<DealSlider> dealImages;
    CategoryAdapter categoryAdapter;
    DealsAdapter dealsAdapter;
    private List<CategoryData> dataList;


    private RecyclerView categoriesRecycler;
    private ApiInterface myInterfaces, myInterfaceDeals;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        myViewPager = view.findViewById(R.id.viewpager);
        myViewPager.setClipToPadding(false);
        myViewPager.setPadding(16, 0, 16, 0);
        myViewPager.setPageMargin(16);
        categoriesRecycler = view.findViewById(R.id.recycleCategory);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriesRecycler.setHasFixedSize(true);

        getImagesForDeals();
        fetchCategories();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Category");

    }

    private void getImagesForDeals() {

        myInterfaceDeals = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DealSlider>> query = myInterfaceDeals.getDealsImage();

        query.enqueue(new Callback<List<DealSlider>>() {
            @Override
            public void onResponse(Call<List<DealSlider>> call, Response<List<DealSlider>> response) {
                if (response.isSuccessful()) {

                    dealImages = response.body();
                    dealsAdapter = new DealsAdapter(dealImages, getActivity());
                    myViewPager.setAdapter(dealsAdapter);


                }
            }

            @Override
            public void onFailure(Call<List<DealSlider>> call, Throwable t) {

            }
        });

    }


    // fetching categories data using Retrofit client
    private void fetchCategories() {
        myInterfaces = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Category> query = myInterfaces.getCategories();

        query.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {


                if (response.isSuccessful()) {

                    dataList = response.body().getData();


                    Log.i("My Result", "onResponse: " + response.body().getData().get(0).getCategoryname());
                    categoryAdapter = new CategoryAdapter(dataList, getActivity());
                    categoriesRecycler.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();

                    //Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });

    }


}
