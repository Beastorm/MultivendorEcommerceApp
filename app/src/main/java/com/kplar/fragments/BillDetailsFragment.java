package com.kplar.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kplar.activities.ShippingAddressFormActivity;
import com.kplar.adapters.BillRowAdapter;
import com.kplar.adapters.ProductForBillAdapter;
import com.kplar.models.billAndProductPackage.BillProduct;
import com.kplar.models.billAndProductPackage.Data;
import com.kplar.models.cartPackage.Cart;
import com.kplar.models.paytmPackage.PaytmChecksum;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillDetailsFragment extends Fragment {

    private CardView paymentByBs;
    private BottomSheetBehavior sheetBehavior;

    private TextView totalAmountTv;
    private BillRowAdapter billRowAdapter;
    private RecyclerView billRecyclerView, productsForBillRecyclerView;
    private ApiInterface apiInterface;
    private MyPrefernces myPrefernces;
    private Context context;
    private String userName, paymentType;
    private double totalAmount;
    private ImageButton addShippingAddressBtn;
    private List<Data> billList;
    private List<com.kplar.models.cartPackage.Data> cartItems;
    private ProductForBillAdapter productForBillAdapter;
    private Button paymentBtn, closeBtn, proceedBtn;
    private RadioGroup radioGroup;
    private RadioButton kplarWalletRb, codRb, othersRb;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_details, container, false);

        totalAmountTv = view.findViewById(R.id.total_amount_tv);
        addShippingAddressBtn = view.findViewById(R.id.add_shipping_address_ib);
        paymentByBs = view.findViewById(R.id.payment_by_bs);
        paymentBtn = view.findViewById(R.id.go_for_pay_btn);

        closeBtn = view.findViewById(R.id.close_btn);
        proceedBtn = view.findViewById(R.id.proceed_btn);

        radioGroup = view.findViewById(R.id.payments_option_rg);
        kplarWalletRb = view.findViewById(R.id.kplar_wallet_rb);
        codRb = view.findViewById(R.id.cod_rb);
        othersRb = view.findViewById(R.id.others_rb);


        billRecyclerView = view.findViewById(R.id.bill_rows_recycler_view);
        billRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        billRecyclerView.setHasFixedSize(true);


        productsForBillRecyclerView = view.findViewById(R.id.product_for_bill_recycler_view);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        productsForBillRecyclerView.setLayoutManager(linearLayoutManager1);
        productsForBillRecyclerView.setHasFixedSize(true);

        myPrefernces = new MyPrefernces(context);
        userName = myPrefernces.readName();


        sheetBehavior = BottomSheetBehavior.from(paymentByBs);
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

        kplarWalletRb.setChecked(true);


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });

        addShippingAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShippingAddressFormActivity.class);
                context.startActivity(intent);

            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });


        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (paymentType == "others") {

                    final String mid = "XqFDwI90885159612284";
                    final String customerId = new MyPrefernces(context).readUserId();
                    final String orderId = generateString().toString().substring(0, 28);
                    final String callBackUrl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                    }


                    getChecksum(mid, customerId, orderId, callBackUrl);

                } else if (paymentType == "cod") {


                } else if (paymentType == "wallet") {


                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.kplar_wallet_rb) {
                    paymentType = "wallet";


                } else if (checkedId == R.id.cod_rb) {
                    kplarWalletRb.setChecked(false);
                    paymentType = "cod";


                } else if (checkedId == R.id.others_rb) {
                    kplarWalletRb.setChecked(false);
                    paymentType = "others";

                }
            }
        });


        return view;
    }


    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    public void getChecksum(final String mid, final String customerId, final String orderId, final String callBackUrl) {


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<PaytmChecksum> query = apiInterface.getChecksum(mid, orderId, customerId,
                "WAP", totalAmount + "", "WEBSTAGING", "Retail", callBackUrl);


        query.enqueue(new Callback<PaytmChecksum>() {
            @Override
            public void onResponse(Call<PaytmChecksum> call, Response<PaytmChecksum> response) {


                if (response.isSuccessful()) {

                    String checkSumHash = response.body().getChecksumhash();


                    PaytmPGService paytmPGService = PaytmPGService.getStagingService("");

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put("MID", "XqFDwI90885159612284");
                    hashMap.put("ORDER_ID", orderId);
                    hashMap.put("CUST_ID", customerId);
                    hashMap.put("CHANNEL_ID", "WAP");
                    hashMap.put("TXN_AMOUNT", totalAmount + "");
                    hashMap.put("WEBSITE", "WEBSTAGING");
                    hashMap.put("INDUSTRY_TYPE_ID", "Retail");
                    hashMap.put("CALLBACK_URL", callBackUrl);
                    hashMap.put("CHECKSUMHASH", checkSumHash);


                    PaytmOrder paytmOrder = new PaytmOrder(hashMap);
                    paytmPGService.initialize(paytmOrder, null);

                    paytmPGService.startPaymentTransaction(context, true, true, new PaytmPaymentTransactionCallback() {
                        @Override
                        public void onTransactionResponse(Bundle inResponse) {

                            Toast.makeText(context, "Payment Transaction Response" + inResponse.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void networkNotAvailable() {
                            Toast.makeText(context, "Network connection error: Check your internet connectivity", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void clientAuthenticationFailed(String inErrorMessage) {
                            Toast.makeText(context, "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void someUIErrorOccurred(String inErrorMessage) {
                            Toast.makeText(context, "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                            Toast.makeText(context, "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onBackPressedCancelTransaction() {
                            Toast.makeText(context, "Transaction cancelled", Toast.LENGTH_LONG).show();
                        }


                        @Override
                        public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                            Toast.makeText(context, "Transaction cancelled", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<PaytmChecksum> call, Throwable t) {

                Toast.makeText(context, "" + t.getCause(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void setProductCost(double productCost) {

        Locale locale = new Locale("en", "IN");
        totalAmountTv.setText(String.format(locale, "%,.2f", productCost));

    }

    @Override
    public void onStart() {
        super.onStart();
        getAllCartItems(userName);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }


    public void getProductWithBill(String userName) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<BillProduct> query = apiInterface.getProductWithBill(userName);

        query.enqueue(new Callback<BillProduct>() {
            @Override
            public void onResponse(Call<BillProduct> call, Response<BillProduct> response) {


                if (response.isSuccessful()) {

                    totalAmount = response.body().getTotal();
                    Locale locale = new Locale("en", "IN");
                    totalAmountTv.setText(String.format(locale, "%,.2f", totalAmount));

                    billList = response.body().getData();

                    billRowAdapter = new BillRowAdapter(billList, context);
                    billRecyclerView.setAdapter(billRowAdapter);
                    billRowAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<BillProduct> call, Throwable t) {


                Toast.makeText(context, "Failed" + t.getCause(), Toast.LENGTH_SHORT).show();

                Log.i("Failed", "onFailure: ffffff" + t.getCause());
            }
        });


    }


    public void getAllCartItems(final String userName) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Cart> query = apiInterface.viewAllCart(userName);

        query.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {

                    if (response.body().getResponse().equals("ok")) {


                        cartItems = response.body().getData();
                        productForBillAdapter = new ProductForBillAdapter(cartItems, context);
                        productsForBillRecyclerView.setAdapter(productForBillAdapter);
                        productForBillAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });


    }


    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
