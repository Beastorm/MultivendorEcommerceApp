package com.kplar.models;

import com.google.gson.annotations.SerializedName;

public class UpdatePassword {
    @SerializedName("response")
    String response;

    public UpdatePassword(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
