package com.kplar.models.productDetailsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteProductsDetails {

    @SerializedName("data")
    @Expose
    private ProductData productData;
    @SerializedName("response")
    @Expose
    private String response;

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
