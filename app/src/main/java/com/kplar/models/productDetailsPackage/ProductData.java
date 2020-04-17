package com.kplar.models.productDetailsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductData {
    @SerializedName("productDetails")
    @Expose
    private ProductDetails productDetails;
    @SerializedName("review")
    @Expose
    private List<Review> review = null;
    @SerializedName("seller")
    @Expose
    private String seller;
    @SerializedName("similarProduct")
    @Expose
    private List<SimilarProduct> similarProduct = null;

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public List<SimilarProduct> getSimilarProduct() {
        return similarProduct;
    }

    public void setSimilarProduct(List<SimilarProduct> similarProduct) {
        this.similarProduct = similarProduct;
    }
}
