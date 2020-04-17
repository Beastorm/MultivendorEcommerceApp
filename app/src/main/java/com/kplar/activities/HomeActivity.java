package com.kplar.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kplar.R;
import com.kplar.fragments.CartFragment;
import com.kplar.fragments.CategoryFragment;
import com.kplar.fragments.HomeFragment;
import com.kplar.fragments.OrderFragment;
import com.kplar.fragments.StarredFragment;
import com.kplar.utilities.MyPrefernces;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener {

    ImageView categorybtn, starredBtn, cartBtn, offersBtn, kplarWallet, myAccount;
    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
    OrderFragment orderFragment;
    CartFragment cartFragment;
    FragmentManager fragmentManager;
    StarredFragment starredFragment;
    Button logoutBtn;
    MyPrefernces myPrefernces;

    DrawerLayout myDrawer;


    BottomNavigationView myHomeBottomNav;

    Toolbar myHomeToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBarDrawerToggle toggle;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        myHomeToolBar = findViewById(R.id.homeToolbar);

        setSupportActionBar(myHomeToolBar);
        categorybtn = findViewById(R.id.categorybtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        starredBtn = findViewById(R.id.starred_btn);
        cartBtn = findViewById(R.id.cart_btn);
        offersBtn = findViewById(R.id.offers_zone);
        kplarWallet = findViewById(R.id.kplar_wallet);
        myAccount = findViewById(R.id.my_account_iv);

        myHomeToolBar.setTitle("MyShoppingFree");
        myHomeToolBar.setNavigationIcon(R.drawable.nav);
        myHomeToolBar.setTitleTextColor(getResources().getColor(R.color.white_color));
        myHomeToolBar.setTitleTextAppearance(this, R.style.myFont);
        myHomeBottomNav = findViewById(R.id.homeBottomNav);
        myPrefernces = new MyPrefernces(this);

        homeFragment = new HomeFragment();
        cartFragment = new CartFragment();
        categoryFragment = new CategoryFragment();
        orderFragment = new OrderFragment();


        myDrawer = findViewById(R.id.drawerLayout);

        setFragment(homeFragment);


        categorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                setFragment(categoryFragment);


            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myPrefernces.writeLoginStatus(false);
                //myDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        starredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(HomeActivity.this, StarredActivity.class);
                startActivity(intent);
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
                myDrawer.closeDrawer(GravityCompat.START);
            }
        });

        offersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OffersActivity.class);
                startActivity(intent);
                myDrawer.closeDrawer(GravityCompat.START);
            }
        });
        kplarWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, KplarWalletActivity.class);
                startActivity(intent);
                myDrawer.closeDrawer(GravityCompat.START);
            }
        });

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(intent);
                myDrawer.closeDrawer(GravityCompat.START);
            }
        });


        // click listener for navigation icon of toolbar
        myHomeToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.openDrawer(GravityCompat.START);

            }
        });


        myHomeBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {

                    case R.id.home:
                        setFragment(homeFragment);
                        break;


                    case R.id.category:
                        setFragment(categoryFragment);
                        break;


                    case R.id.cart:
                        Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                        startActivity(intent);
                        break;


                }
                return true;


            }
        });


    }

    private void setFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeContainer, fragment);
            fragmentTransaction.addToBackStack("OP");
            fragmentTransaction.commit();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.home:

                return true;

            case R.id.category:


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (homeFragment.isVisible()) {
            myHomeBottomNav.setSelectedItemId(R.id.home);
        }
        if (categoryFragment.isVisible()) {
            myHomeBottomNav.setSelectedItemId(R.id.category);
        }
        if (cartFragment.isVisible()) {
            myHomeBottomNav.setSelectedItemId(R.id.cart);
        }

    }*/
}
