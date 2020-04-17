package com.kplar.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kplar.R;
import com.kplar.adapters.NewProductAdapter;
import com.kplar.adapters.OfferSliderAdapter;
import com.kplar.adapters.RecentViewAdapter;
import com.kplar.adapters.TopCategoryAdapter;
import com.kplar.models.OfferSliders;
import com.kplar.models.newProductsPackage.Data;
import com.kplar.models.newProductsPackage.NewProduct;
import com.kplar.models.recentViewPackage.Product;
import com.kplar.models.recentViewPackage.RecentView;
import com.kplar.models.topCategoriesPackage.TopCategorySlider;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    ViewPager myViewPager;
    List<OfferSliders> offerImages;
    List<Product> productList;
    Timer timer;
    int currentPosition;
    Context context;
    ConstraintLayout recentContainer, newProductContainer;

    private RecyclerView newProductsRecyclerView;
    NewProductAdapter newProductAdapter;
    List<Data> newProductList;

    private RecyclerView recentViewRecyclerView;
    private RecentViewAdapter recentViewAdapter;

    private OfferSliderAdapter offerSliderAdapter;
    private ApiInterface myInterface, sliderInterface;
    private RecyclerView myRecycler;
    private TopCategoryAdapter myAdapter;
    private List<TopCategorySlider> imagelist;
    String userName;


    private MyPrefernces myPrefernces;


    private OnFragmentInteractionListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        myRecycler = view.findViewById(R.id.recyclerView);
        myViewPager = view.findViewById(R.id.viewPager);
        recentContainer = view.findViewById(R.id.recent_container);
        newProductContainer = view.findViewById(R.id.new_products_container);

        recentViewRecyclerView = view.findViewById(R.id.recent_view_recycler_view);

        newProductsRecyclerView = view.findViewById(R.id.new_product_recycler_view);

        myPrefernces = new MyPrefernces(getActivity());


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MyFreeShopping");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecycler.setLayoutManager(linearLayoutManager);
        myRecycler.setHasFixedSize(true);


        LinearLayoutManager recentViewLinearLayoutManager = new LinearLayoutManager(getActivity());
        recentViewLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recentViewRecyclerView.setLayoutManager(recentViewLinearLayoutManager);
        recentViewRecyclerView.setHasFixedSize(true);


        GridLayoutManager newProductGLM = new GridLayoutManager(getActivity(), 2);
        newProductGLM.setOrientation(LinearLayoutManager.VERTICAL);
        newProductsRecyclerView.setLayoutManager(newProductGLM);
        newProductsRecyclerView.setHasFixedSize(true);

        userName = myPrefernces.readName();


        getImagesForTopCategory();
        getImagesForSlider();

    }


    //Getting Retrofit client and making request for images url of top category
    public void getImagesForTopCategory() {

        myInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<TopCategorySlider>> query = myInterface.getSliderItems();

        query.enqueue(new Callback<List<TopCategorySlider>>() {
            @Override
            public void onResponse(Call<List<TopCategorySlider>> call, Response<List<TopCategorySlider>> response) {

                if (response.isSuccessful()) {


                    imagelist = response.body();
                    Log.i("My Result", "onResponse1: " + response.body());
                    //Toast.makeText(getContext(), "Inside Response"+imagelist, Toast.LENGTH_SHORT).show();
                    myAdapter = new TopCategoryAdapter(imagelist, getActivity());
                    myRecycler.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onFailure(Call<List<TopCategorySlider>> call, Throwable t) {

            }
        });


    }


    public void getImagesForSlider() {

        sliderInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<OfferSliders>> query = sliderInterface.getSlider();
        query.enqueue(new Callback<List<OfferSliders>>() {
            @Override
            public void onResponse(Call<List<OfferSliders>> call, Response<List<OfferSliders>> response) {
                if (response.isSuccessful()) {

                    offerImages = response.body();
                    offerSliderAdapter = new OfferSliderAdapter(offerImages, getActivity());
                    myViewPager.setAdapter(offerSliderAdapter);
                    createSlideShow();


                }
            }

            @Override
            public void onFailure(Call<List<OfferSliders>> call, Throwable t) {

            }
        });


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        viewRecentProducts();
        getNewProductList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    // slide show for offerSlider
    public void createSlideShow() {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = myViewPager.getCurrentItem();
                if (currentPosition == offerImages.size() - 1) {
                    currentPosition = -1;
                }
                myViewPager.setCurrentItem(++currentPosition, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 2000, 5000);


    }


    public void viewRecentProducts() {
        myInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecentView> query = myInterface.viewRecentProducts(userName);

        query.enqueue(new Callback<RecentView>() {
            @Override
            public void onResponse(Call<RecentView> call, Response<RecentView> response) {

                if (response.isSuccessful() && response.body().getResponse().equals("ok")) {

                    recentContainer.setVisibility(View.VISIBLE);

                    productList = response.body().getProduct();
                    recentViewAdapter = new RecentViewAdapter(productList, getActivity());
                    recentViewRecyclerView.setAdapter(recentViewAdapter);
                    recentViewAdapter.notifyDataSetChanged();
                } else {

                    recentContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RecentView> call, Throwable t) {
                recentContainer.setVisibility(View.GONE);
            }
        });

    }


    public void getNewProductList() {

        myInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<NewProduct> query = myInterface.getNewProducts();

        query.enqueue(new Callback<NewProduct>() {
            @Override
            public void onResponse(Call<NewProduct> call, Response<NewProduct> response) {
                if (response.isSuccessful()) {

                    if (response.body().getResponse().equals("ok")) {

                        newProductContainer.setVisibility(View.VISIBLE);


                        newProductList = response.body().getData();
                        newProductAdapter = new NewProductAdapter(newProductList, getActivity());

                        newProductsRecyclerView.setAdapter(newProductAdapter);
                        newProductAdapter.notifyDataSetChanged();


                    } else {

                        newProductContainer.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<NewProduct> call, Throwable t) {
                newProductContainer.setVisibility(View.GONE);

            }
        });


    }


    @Override
    public void onDestroyView() {
        if (timer != null) {
            timer.cancel();
        }


        super.onDestroyView();
    }
}
