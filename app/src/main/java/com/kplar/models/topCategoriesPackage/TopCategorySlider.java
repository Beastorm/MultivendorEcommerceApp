package com.kplar.models.topCategoriesPackage;

import com.google.gson.annotations.SerializedName;

public class TopCategorySlider {


    @SerializedName("picpath")
    private String picpath;
    @SerializedName("id")
    private String id;

    public TopCategorySlider(String picpath, String id) {
        this.picpath = picpath;
        this.id = id;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
