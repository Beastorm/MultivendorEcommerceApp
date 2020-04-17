package com.kplar.models.subcategoriesPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryData {
    public SubCategoryData(String content, String status, String dsecription, String image, String name, String catId, String id) {
        this.content = content;
        this.status = status;
        this.dsecription = dsecription;
        this.image = image;
        this.name = name;
        this.catId = catId;
        this.id = id;
    }

    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("dsecription")
    private String dsecription;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("cat_id")
    private String catId;
    @Expose
    @SerializedName("id")
    private String id;

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

    public String getDsecription() {
        return dsecription;
    }

    public void setDsecription(String dsecription) {
        this.dsecription = dsecription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
