package com.kplar.models.offerPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Offer {


    @Expose
    @SerializedName("currentDate")
    private String currentdate;
    @Expose
    @SerializedName("response")
    private String response;
    @Expose
    @SerializedName("data")
    private List<Data> data;

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

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
