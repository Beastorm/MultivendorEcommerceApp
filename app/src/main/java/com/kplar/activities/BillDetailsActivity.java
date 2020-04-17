package com.kplar.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kplar.fragments.BillDetailsFragment;
import com.kplar.R;
import com.kplar.utilities.MyPrefernces;

public class BillDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    BillDetailsFragment billDetailsFragment;
    FragmentManager fragmentManager;
    MyPrefernces myPrefernces;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        toolbar = findViewById(R.id.bill_details_tool_bar);


        toolbar.setTitle("Product(s) and Bill");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_color));
        toolbar.setTitleTextAppearance(this, R.style.myFont);

        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




        myPrefernces = new MyPrefernces(this);
        userName = myPrefernces.readName();


        billDetailsFragment = new BillDetailsFragment();
        setFragment(billDetailsFragment);

        billDetailsFragment.getProductWithBill(userName);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_and_bill_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.help_menu_item:

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
            fragmentTransaction.replace(R.id.bill_details_container, fragment);
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
