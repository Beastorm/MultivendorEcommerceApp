package com.kplar.models.paytmPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class PaytmChecksum {


    @Expose
    @SerializedName("payt_STATUS")
    private String paytStatus;
    @Expose
    @SerializedName("CHECKSUMHASH")
    private String checksumhash;

    public String getPaytStatus() {
        return paytStatus;
    }

    public void setPaytStatus(String paytStatus) {
        this.paytStatus = paytStatus;
    }

    public String getChecksumhash() {
        return checksumhash;
    }

    public void setChecksumhash(String checksumhash) {
        this.checksumhash = checksumhash;
    }
}
