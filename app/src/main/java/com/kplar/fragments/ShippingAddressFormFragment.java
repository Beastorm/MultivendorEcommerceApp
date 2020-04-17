package com.kplar.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kplar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingAddressFormFragment extends Fragment {


    public ShippingAddressFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipping_address_form, container, false);
    }

}
