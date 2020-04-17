package com.kplar.models.categoriesPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryData {




    @Expose
    @SerializedName("flag")
    private String flag;
    @Expose
    @SerializedName("content")
    private String content;


    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("categoryname")
    private String categoryname;
    @Expose
    @SerializedName("id")
    private String id;


    public CategoryData(String flag, String content, String status, String image, String categoryname, String id) {
        this.flag = flag;
        this.content = content;
        this.status = status;
        this.image = image;
        this.categoryname = categoryname;
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
