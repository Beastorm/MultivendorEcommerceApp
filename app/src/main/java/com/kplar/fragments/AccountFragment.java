package com.kplar.fragments;

import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kplar.R;
import com.kplar.activities.ShippingAddressFormActivity;
import com.kplar.utilities.MyPrefernces;

public class AccountFragment extends Fragment {

    private TextView username, errorTv;
    private Context context;
    private ImageButton editBtn, addAddressBtn;
    private EditText nameEt, emailEt, mobileNoEt;
    Button saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_activity, container, false);

        username = view.findViewById(R.id.user_name);
        editBtn = view.findViewById(R.id.edit_btn);
        nameEt = view.findViewById(R.id.name_et);
        mobileNoEt = view.findViewById(R.id.mobile_no_et);
        emailEt = view.findViewById(R.id.email_et);
        errorTv = view.findViewById(R.id.error_tv);
        saveBtn = view.findViewById(R.id.save_btn);
        addAddressBtn = view.findViewById(R.id.add_address_img_btn);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        username.setText(new MyPrefernces(context).readName());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileNoEt.setAlpha(1);
                mobileNoEt.setEnabled(true);
                mobileNoEt.setBackgroundColor(Color.parseColor("#ffffff"));


                emailEt.setAlpha(1);
                emailEt.setEnabled(true);
                emailEt.setBackgroundColor(Color.parseColor("#ffffff"));
                // emailEt.setHorizontallyScrolling(true);

                nameEt.setAlpha(1);
                nameEt.setEnabled(true);
                nameEt.setBackgroundColor(Color.parseColor("#ffffff"));
                nameEt.requestFocus();

                editBtn.setVisibility(View.GONE);
                saveBtn.setVisibility(View.VISIBLE);

            }
        });


        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s.toString())) {


                } else {


                }

            }
        });

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mobileNoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShippingAddressFormActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;

    }
}
