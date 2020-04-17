package com.kplar.utilities;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static final String BASE_URL = "http://restapi.purushotamautomation.co.in/shopping/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            Log.i("Info", "Inside Retrofit");
        }

        return retrofit;
    }
}
