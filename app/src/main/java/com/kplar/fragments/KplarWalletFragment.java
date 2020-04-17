package com.kplar.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kplar.models.kplarWalletPackage.WalletBalance;
import com.kplar.models.paytmPackage.PaytmChecksum;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KplarWalletFragment extends Fragment {
    private BottomSheetBehavior sheetBehavior;
    private ConstraintLayout addMoneyByBs;
    ImageButton addMoneyBtn;
    EditText addMoneyEt;
    double addedAmount;
    Button proceedToAddBtn;
    TextView errorTv, balanceTv;
    ApiInterface apiInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kplar_wallet, container, false);

        addMoneyByBs = view.findViewById(R.id.bs_add_money);
        addMoneyBtn = view.findViewById(R.id.add_money_ib);
        addMoneyEt = view.findViewById(R.id.add_money_et);
        proceedToAddBtn = view.findViewById(R.id.proceed_to_add_btn);
        errorTv = view.findViewById(R.id.error_tv);
        balanceTv = view.findViewById(R.id.balance_tv);


        sheetBehavior = BottomSheetBehavior.from(addMoneyByBs);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        viewWalletBalance();

        addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleBottomSheet();

            }
        });


        addMoneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double tempAmount = Double.parseDouble(s.toString().trim());
                    if (!TextUtils.isEmpty(s.toString().trim())) {
                        if (!(tempAmount < 1)) {
                            addedAmount = tempAmount;

                            proceedToAddBtn.setEnabled(true);
                            proceedToAddBtn.setTextColor(Color.parseColor("#ffffff"));
                            errorTv.setVisibility(View.GONE);

                        } else {
                            proceedToAddBtn.setEnabled(false);
                            proceedToAddBtn.setTextColor(Color.parseColor("#70ffffff"));
                            errorTv.setVisibility(View.VISIBLE);
                            errorTv.setText("Please Enter Valid Amount");


                        }

                    } else {

                        proceedToAddBtn.setEnabled(false);
                        proceedToAddBtn.setTextColor(Color.parseColor("#70ffffff"));
                        errorTv.setVisibility(View.VISIBLE);
                        errorTv.setText("Please Enter Valid Amount");
                    }

                } catch (NumberFormatException e) {
                    proceedToAddBtn.setEnabled(false);
                    proceedToAddBtn.setTextColor(Color.parseColor("#70ffffff"));
                    errorTv.setVisibility(View.VISIBLE);
                    errorTv.setText("Please Enter Valid Amount");
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();

                }

            }
        });

        proceedToAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mid = "XqFDwI90885159612284";
                final String customerId = new MyPrefernces(getActivity()).readUserId();
                final String orderId = generateString().toString().substring(0, 28);
                final String callBackUrl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                }


                getChecksum(mid, customerId, orderId, callBackUrl);

            }
        });


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
                "WAP", addedAmount + "", "WEBSTAGING", "Retail", callBackUrl);


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
                    hashMap.put("TXN_AMOUNT", addedAmount + "");
                    hashMap.put("WEBSITE", "WEBSTAGING");
                    hashMap.put("INDUSTRY_TYPE_ID", "Retail");
                    hashMap.put("CALLBACK_URL", callBackUrl);
                    hashMap.put("CHECKSUMHASH", checkSumHash);


                    PaytmOrder paytmOrder = new PaytmOrder(hashMap);
                    paytmPGService.initialize(paytmOrder, null);

                    paytmPGService.startPaymentTransaction(getActivity(), true, true, new PaytmPaymentTransactionCallback() {
                        @Override
                        public void onTransactionResponse(Bundle inResponse) {

                            if (inResponse.getString("STATUS").equals("TXN_SUCCESS")) {
                                addMoneyInWallet(inResponse.getString("TXNAMOUNT"));


                            }


                            Toast.makeText(getActivity(), "Payment Transaction Response" + inResponse.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void networkNotAvailable() {
                            Toast.makeText(getActivity(), "Network connection error: Check your internet connectivity", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void clientAuthenticationFailed(String inErrorMessage) {
                            Toast.makeText(getActivity(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void someUIErrorOccurred(String inErrorMessage) {
                            Toast.makeText(getActivity(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                            Toast.makeText(getActivity(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onBackPressedCancelTransaction() {
                            Toast.makeText(getActivity(), "Transaction cancelled", Toast.LENGTH_LONG).show();
                        }


                        @Override
                        public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                            Toast.makeText(getActivity(), "Transaction cancelled", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<PaytmChecksum> call, Throwable t) {

                Toast.makeText(getContext(), "" + t.getCause(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addMoneyInWallet(String txnAmount) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> query = apiInterface.addMoneyToWallet(new MyPrefernces(getActivity()).readName(), txnAmount);
        query.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Amount Added Successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }


    private void viewWalletBalance() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<WalletBalance> query = apiInterface.viewWalletMoney(new MyPrefernces(getActivity()).readName());

        query.enqueue(new Callback<WalletBalance>() {
            @Override
            public void onResponse(Call<WalletBalance> call, Response<WalletBalance> response) {
                if (response.isSuccessful() && response.body().getResponse().equals("true")) {

                    String myBalance = response.body().getData().getWallet();
                    balanceTv.setText(myBalance);
                }
            }

            @Override
            public void onFailure(Call<WalletBalance> call, Throwable t) {

            }
        });

    }
}
