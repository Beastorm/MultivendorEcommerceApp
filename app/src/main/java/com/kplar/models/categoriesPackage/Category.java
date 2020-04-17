package com.kplar.models.categoriesPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {


    @Expose
    @SerializedName("response")
    private String response;
    @Expose
    @SerializedName("data")
    private List<CategoryData> data;

    public Category(String response, List<CategoryData> data) {
        this.response = response;
        this.data = data;
    }



    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CategoryData> getData() {
        return data;
    }

    public void setData(List<CategoryData> data) {
        this.data = data;
    }
}
