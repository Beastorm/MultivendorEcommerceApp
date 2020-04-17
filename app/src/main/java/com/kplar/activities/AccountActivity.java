package com.kplar.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kplar.fragments.AccountFragment;
import com.kplar.R;

public class AccountActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        accountFragment = new AccountFragment();
    }


    private void setFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            //fragmentTransaction.addToBackStack("OP");
            fragmentTransaction.commit();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        setFragment(accountFragment);


    }
}
