package com.kplar.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kplar.fragments.ShippingAddressFormFragment;
import com.kplar.R;

public class ShippingAddressFormActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentManager fragmentManager;
    ShippingAddressFormFragment shippingAddressFormFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address_form_activty);

        toolbar = findViewById(R.id.shipping_form_tool_bar);


        toolbar.setTitle("Shipping Address");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_color));
        toolbar.setTitleTextAppearance(this, R.style.myFont);

        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        shippingAddressFormFragment = new ShippingAddressFormFragment();
        setFragment(shippingAddressFormFragment);
    }


    private void setFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.shipping_form_container, fragment);
            //fragmentTransaction.addToBackStack("OP");
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
