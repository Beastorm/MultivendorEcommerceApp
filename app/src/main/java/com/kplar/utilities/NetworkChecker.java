package com.kplar.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkChecker {

    Context context;

    public NetworkChecker(Context context) {
        this.context = context;
    }

    public boolean internetStatus(){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnected();




    }
}
