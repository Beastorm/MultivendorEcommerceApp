package com.kplar.models.productDetailsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {

    @SerializedName("data")
    @Expose
    private List<ReviewList> reviewList = null;
    @SerializedName("response")
    @Expose
    private String response;

    public List<ReviewList> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewList> reviewList) {
        this.reviewList = reviewList;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
