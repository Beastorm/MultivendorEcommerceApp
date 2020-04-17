package com.kplar.models.newProductsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewProduct {


    @Expose
    @SerializedName("response")
    private String response;
    @Expose
    @SerializedName("data")
    private List<Data> data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
