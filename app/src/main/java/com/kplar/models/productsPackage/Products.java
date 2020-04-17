package com.kplar.models.productsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @Expose
    @SerializedName("response")
    private String response;
    @Expose
    @SerializedName("data")
    private List<ProductsData> data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ProductsData> getData() {
        return data;
    }

    public void setData(List<ProductsData> data) {
        this.data = data;
    }
}
