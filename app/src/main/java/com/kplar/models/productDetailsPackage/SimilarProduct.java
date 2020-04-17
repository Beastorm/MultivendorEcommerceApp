package com.kplar.models.productDetailsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarProduct {


    @SerializedName("data")
    @Expose
    private List<ProductList> productList = null;
    @SerializedName("response")
    @Expose
    private String response;

    public List<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList> productList) {
        this.productList = productList;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
