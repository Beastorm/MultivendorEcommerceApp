package com.kplar.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kplar.adapters.WishListAdapter;
import com.kplar.fragments.StarredFragment;
import com.kplar.R;
import com.kplar.utilities.MyPrefernces;

public class StarredActivity extends AppCompatActivity implements WishListAdapter.WishListCommunicator {

    Toolbar starredToolbar;
    StarredFragment starredFragment;
    FragmentManager fragmentManager;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        starredToolbar = findViewById(R.id.starredToolbar);
        setSupportActionBar(starredToolbar);
        starredToolbar.setTitle("Starred");

        starredToolbar.setTitleTextColor(getResources().getColor(R.color.white_color));
        starredToolbar.setTitleTextAppearance(this, R.style.myFont);
        if (starredToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            starredToolbar.setTitle("Starred");
            starredToolbar.setTitleTextColor(getResources().getColor(R.color.white_color));
            starredToolbar.setTitleTextAppearance(this, R.style.myFont);
        }

        starredFragment = new StarredFragment();
        setFragment(starredFragment);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.starred_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        menuItem = item;


        switch (item.getItemId()) {
            case R.id.clear_all:
                if (starredFragment != null) {
                    starredFragment.removeAllWishList(new MyPrefernces(getApplicationContext()).readName());
                    starredFragment.getAllWishList(new MyPrefernces(getApplicationContext()).readName());
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.starredContainer, fragment);
            //fragmentTransaction.addToBackStack("wishList");
            fragmentTransaction.commit();
            //starredFragment.getMenuItem(menuItem);
        }

    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void delWishListItem(String productId) {
        if (starredFragment != null) {
            starredFragment.delToMyWishList(productId, new MyPrefernces(getApplicationContext()).readName());
            starredFragment.getAllWishList(new MyPrefernces(getApplicationContext()).readName());

        }
    }
}
