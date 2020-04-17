package com.kplar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kplar.adapters.OfferAdapter;
import com.kplar.models.offerPackage.Data;
import com.kplar.models.offerPackage.Offer;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OffersActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton currentBtn, redeemedBtn, expiredBtn;
    TextView userName, noOfferMsg;
    ApiInterface apiInterface;
    OfferAdapter offerAdapter;
    List<Data> offerList;


    RecyclerView offerRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        radioGroup = findViewById(R.id.radioGroup);
        currentBtn = findViewById(R.id.current_btn);
        redeemedBtn = findViewById(R.id.redeemed_btn);
        expiredBtn = findViewById(R.id.expired_btn);
        noOfferMsg = findViewById(R.id.no_offers);

        offerRecyclerView = findViewById(R.id.offers_recycler_view);
        offerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        offerRecyclerView.setHasFixedSize(true);


        userName = findViewById(R.id.user_name);


        userName.setText(new MyPrefernces(this).readName());

        currentBtn.setChecked(true);
        getOffersList("2");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.current_btn) {

                    getOffersList("2");

                } else if (checkedId == R.id.redeemed_btn) {
                    getOffersList("1");


                } else if (checkedId == R.id.expired_btn) {

                    getOffersList("3");


                }
            }
        });


    }


    public void getOffersList(String section) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Offer> call1 = apiInterface.viewOffers(section);

        call1.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {

                if (response.isSuccessful()&& response.body().getResponse().equals("ok")) {

                    offerRecyclerView.setVisibility(View.VISIBLE);
                    noOfferMsg.setVisibility(View.INVISIBLE);
                    offerList = response.body().getData();
                    offerAdapter = new OfferAdapter(offerList, OffersActivity.this);

                    offerRecyclerView.setAdapter(offerAdapter);

                    offerAdapter.notifyDataSetChanged();


                }
                else {

                    offerRecyclerView.setVisibility(View.INVISIBLE);
                    noOfferMsg.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {

            }
        });


    }

}
