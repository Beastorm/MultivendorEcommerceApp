package com.kplar.models.recentViewPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentView {

    @Expose
    @SerializedName("response")
    private String response;
    @Expose
    @SerializedName("product")
    private List<Product> product;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
