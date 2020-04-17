package com.kplar.models.productDetailsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageSet {

    @SerializedName("pic_path")
    @Expose
    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
