package com.kplar.activities;

import com.google.gson.annotations.SerializedName;

public class ForgotPassword {

    @SerializedName("otp")
    private String otp;
    @SerializedName("response")
    private  String response;

    //Constructor
    public ForgotPassword(String otp, String response) {
        this.otp = otp;
        this.response = response;
    }

    //Getters and Setters
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
