package com.kplar.models.newProductsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Imageset {
    @Expose
    @SerializedName("pic_path")
    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
