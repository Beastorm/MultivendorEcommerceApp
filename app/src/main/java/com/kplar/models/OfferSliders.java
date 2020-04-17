package com.kplar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferSliders {


    @SerializedName("pic_path")
    @Expose
    String picPath;
    @SerializedName("id")
    @Expose
    String id;

    public OfferSliders(String picPath, String id) {
        this.picPath = picPath;
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
