package com.kplar.models.kplarWalletPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class WalletBalance {

    @Expose
    @SerializedName("response")
    private String response;
    @Expose
    @SerializedName("data")
    private Data data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
