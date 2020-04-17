package com.kplar.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kplar.adapters.CartAdapter;
import com.kplar.fragments.CartFragment;
import com.kplar.R;
import com.kplar.utilities.MyPrefernces;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartCommunicator {
    FragmentManager fragmentManager;
    CartFragment cartFragment;
    Toolbar toolbar;
    MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cart_toolbar);


        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_color));
        toolbar.setTitleTextAppearance(this, R.style.myFont);

        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (cartFragment == null) {
            cartFragment = new CartFragment();
            setFragment(cartFragment);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.starred_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        menuItem = item;


        switch (item.getItemId()) {
            case R.id.clear_all:
                if (cartFragment != null) {
                    cartFragment.removeAllCart(new MyPrefernces(getApplicationContext()).readName());
                    cartFragment.getAllCartItems(new MyPrefernces(getApplicationContext()).readName());

                }
                return true;


            case R.id.category:


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.cart_container, fragment);
            //fragmentTransaction.addToBackStack("OP");
            fragmentTransaction.commit();
        }


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void delCartItem(String productId) {
        if (cartFragment != null) {
            cartFragment.delToMyCart(productId, new MyPrefernces(getApplicationContext()).readName());
            cartFragment.getAllCartItems(new MyPrefernces(getApplicationContext()).readName());


        }


    }


    public void refreshFragment(Fragment fragment){




    }


    // leave empty implementation
    @Override
    public void getAllProductIdWithTotalCost(String[] productIds, double totalCost) {


    }

    @Override
    public void getProductDetails(String productId, String qty) {

        if (cartFragment != null) {
            cartFragment.updateProductQty(productId, new MyPrefernces(getApplicationContext()).readName(), qty);
            cartFragment.getTotalCost(new MyPrefernces(getApplicationContext()).readName());

        }


    }


}
