package com.kplar.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kplar.adapters.ProductsImagesAdapter;
import com.kplar.adapters.ReviewsAdapter;
import com.kplar.adapters.SimilarProductsAdapter;
import com.kplar.fragments.CreateReviewDialogFragment;
import com.kplar.fragments.ViewSellerDialogFragment;
import com.kplar.models.productDetailsPackage.CompleteProductsDetails;
import com.kplar.models.productDetailsPackage.ImageSet;
import com.kplar.models.productDetailsPackage.ProductList;
import com.kplar.models.productDetailsPackage.Review;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements CreateReviewDialogFragment.onSomeEventListener, SimilarProductsAdapter.OnSimilarProductListener {

    double mrps, discountPer, discountPriceno;
    TextView productName, brandName, mrp, discountPrice, productDesc, no_reviewTxt, similarProductTxt;
    ApiInterface apiInterface, addReviewApiInterface, addWishlistInterface, removeToWishListInterface, cartApiInterface;
    Call<CompleteProductsDetails> call1;
    List<ImageSet> imageSets;
    List<Review> reviewList;
    List<ProductList> productLists;
    Toolbar toolbar;
    int customPosition = 0;
    ViewPager viewPager;
    LinearLayout linearLayout;
    ProductsImagesAdapter productsImagesAdapter;
    Button viewSellerBtn, buyNowBtn;
    ViewSellerDialogFragment viewSellerDialogFragment;
    String aboutSeller;
    ReviewsAdapter reviewsAdapter;
    RecyclerView recyclerView, similarProductRecyclerView;
    LinearLayoutManager linearLayoutManager;
    SimilarProductsAdapter similarProductsAdapter;
    ImageView createReview;
    CreateReviewDialogFragment createReviewDialog;
    ScrollView scrollView;
    ToggleButton toggleButton;
    MyPrefernces myPrefernces;
    String productId;
    ConstraintLayout addCart;
    double productCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        toolbar = findViewById(R.id.productDetailsToolbar);
        viewPager = findViewById(R.id.productImgsViewPager);
        toggleButton = findViewById(R.id.toggleButton);
        linearLayout = findViewById(R.id.dotContainer);
        viewSellerBtn = findViewById(R.id.viewSellerBtn);
        createReview = findViewById(R.id.createReview);
        productName = findViewById(R.id.productName);
        brandName = findViewById(R.id.brand_name);
        mrp = findViewById(R.id.mrp);
        discountPrice = findViewById(R.id.discountPrice);
        productDesc = findViewById(R.id.productDesc);
        no_reviewTxt = findViewById(R.id.no_reviewTxt);
        recyclerView = findViewById(R.id.reviewList);
        similarProductRecyclerView = findViewById(R.id.similarProductRecyclerView);
        similarProductTxt = findViewById(R.id.similarProductTxt);
        scrollView = findViewById(R.id.scrollView);
        addCart = findViewById(R.id.add_cart);
        buyNowBtn = findViewById(R.id.buy_now_btn);


        productId = getIntent().getStringExtra("productId");

        myPrefernces = new MyPrefernces(this);


        //reviews list linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        //similar product list layout manager
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        similarProductRecyclerView.setLayoutManager(linearLayoutManager1);
        similarProductRecyclerView.setHasFixedSize(true);


        //set up for toolbar
        toolbar.setTitle("MFS");
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("productName"));
        toolbar.setTitleTextAppearance(this, R.style.myFont);
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        // getting all product details
        getCompleteProductDetails(productId);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                customPosition = position;
                prePareDots(customPosition++);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewSellerDialogFragment = new ViewSellerDialogFragment();
                viewSellerDialogFragment.show(getSupportFragmentManager(), "Seller Dialog");
                viewSellerDialogFragment.getSellerData(aboutSeller);


            }
        });

        createReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReviewDialog = new CreateReviewDialogFragment();
                createReviewDialog.show(getSupportFragmentManager(), "Review Dialog");

            }
        });


        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCart();


            }
        });


        /*buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductDetailsActivity.this, BillDetailsActivity.class);
                intent.putExtra("source", "ProductDetailsActivity");
                intent.putExtra("productCost", productCost);
                startActivity(intent);
            }
        });*/

        // starred button click operation
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Toast.makeText(ProductDetailsActivity.this, "Selected", Toast.LENGTH_SHORT).show();


                    addToMyWishList();


                } else {
                    //Toast.makeText(ProductDetailsActivity.this, "unselected", Toast.LENGTH_SHORT).show();

                    delToMyWishList();

                }
            }
        });

    }


    private void addToMyWishList() {

        addWishlistInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call1 = addWishlistInterface.addProduct2Wishlist(productId, myPrefernces.readName());

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Added to  Your WishList  " + productId + myPrefernces.readName(), Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ProductDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();


            }
        });


    }


    private void delToMyWishList() {

        removeToWishListInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call1 = removeToWishListInterface.removeProductToWishList(productId, myPrefernces.readName());

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Removed From Your WishList", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void getCompleteProductDetails(String productId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        call1 = apiInterface.getProductDetails(productId);
        call1.enqueue(new Callback<CompleteProductsDetails>() {


            @Override
            public void onResponse(Call<CompleteProductsDetails> call, Response<CompleteProductsDetails> response) {

                if (response.isSuccessful()) {


                    // seller information
                    aboutSeller = response.body().getProductData().getSeller();

                    // getting all reviews of a product
                    if (response.body().getProductData().getReview().get(0).getResponse().equals("ok")) {
                        no_reviewTxt.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        reviewList = response.body().getProductData().getReview();


                        double mrp, discountPer;
                        mrp = Double.parseDouble(response.body().getProductData().getProductDetails().getProductPrice());
                        discountPer = Double.parseDouble(response.body().getProductData().getProductDetails().getDiscount());
                        productCost = mrp - ((discountPer / 100) * mrp);


                        reviewsAdapter = new ReviewsAdapter(reviewList, ProductDetailsActivity.this);
                        recyclerView.setAdapter(reviewsAdapter);
                        reviewsAdapter.notifyDataSetChanged();
                    } else {
                        no_reviewTxt.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);

                    }


                    //getting similar products
                    if (response.body().getProductData().getSimilarProduct().get(0).getResponse().equals("ok")) {

                        similarProductRecyclerView.setVisibility(View.VISIBLE);
                        similarProductTxt.setVisibility(View.VISIBLE);
                        productLists = response.body().getProductData().getSimilarProduct().get(0).getProductList();
                        similarProductsAdapter = new SimilarProductsAdapter(productLists, ProductDetailsActivity.this);
                        similarProductRecyclerView.setAdapter(similarProductsAdapter);
                        similarProductsAdapter.notifyDataSetChanged();


                    } else {
                        similarProductRecyclerView.setVisibility(View.INVISIBLE);
                        similarProductTxt.setVisibility(View.INVISIBLE);

                    }

                    // discount price calculation
                    mrps = Double.parseDouble(response.body().getProductData().getProductDetails().getProductPrice());
                    discountPer = Double.parseDouble(response.body().getProductData().getProductDetails().getDiscount());
                    discountPriceno = mrps - ((discountPer / 100) * mrps);

                    productName.setText(response.body().getProductData().getProductDetails().getProductName());
                    brandName.setText(response.body().getProductData().getProductDetails().getBrand());
                    mrp.setText(response.body().getProductData().getProductDetails().getProductPrice());

                    discountPrice.setText(Double.toString(discountPriceno));
                    productDesc.setText(response.body().getProductData().getProductDetails().getProductDescripition());

                    // Getting Product images
                    imageSets = response.body().getProductData().getProductDetails().getImageSet();
                    productsImagesAdapter = new ProductsImagesAdapter(imageSets, ProductDetailsActivity.this);
                    viewPager.setAdapter(productsImagesAdapter);
                    if (imageSets.size() > 0)
                        prePareDots(customPosition);


                }
            }

            @Override
            public void onFailure(Call<CompleteProductsDetails> call, Throwable t) {

                Toast.makeText(ProductDetailsActivity.this, "Failed" + t.getCause(), Toast.LENGTH_SHORT).show();
                // Log.i("mmmmmmmm", "onFailure: " + t.getCause());

            }
        });
    }


    private void prePareDots(int currentPosition) {

        if (linearLayout.getChildCount() > 0)
            linearLayout.removeAllViews();


        ImageView[] dots = new ImageView[imageSets.size()];

        for (int i = 0; i < imageSets.size(); i++) {

            dots[i] = new ImageView(this);
            if (i == currentPosition)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));

            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.normal_dot));


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(4, 0, 4, 0);
            linearLayout.addView(dots[i], layoutParams);


        }


    }


    @Override
    public void someEvent(String userName, String message, float rating) {
        addReviewApiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<ResponseBody> query = addReviewApiInterface.addReview(userName, getIntent().getStringExtra("productId"), rating + "", message);

        query.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                createReviewDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Thanks For Review", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public void fetchSimilarProductData(String productId, String productNameStr) {
        getCompleteProductDetails(productId);
        toolbar.setTitle(productNameStr);
        customPosition = 0;
        scrollView.smoothScrollTo(0, 0);


    }


    public void addCart() {
        cartApiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call1 = apiInterface.addProduct2Cart(productId, myPrefernces.readName(), "1");
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


    public void addProductInRecentList() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.addToRecentProductList(myPrefernces.readName(), productId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(ProductDetailsActivity.this, "Added To Recent View", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ProductDetailsActivity.this, "fAILED TO ADD IN RECENT", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        addProductInRecentList();
    }
}
