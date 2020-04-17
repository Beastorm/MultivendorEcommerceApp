package com.kplar.models.subcategoriesPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategory {


    @Expose
    @SerializedName("response")
    private String response;
    @Expose
    @SerializedName("data")
    private List<SubCategoryData> data;

    public SubCategory(String response, List<SubCategoryData> data) {
        this.response = response;
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<SubCategoryData> getData() {
        return data;
    }

    public void setData(List<SubCategoryData> data) {
        this.data = data;
    }
}
