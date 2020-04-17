package com.kplar.models.productsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsData {
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("subcat_id")
    private String subcatId;
    @Expose
    @SerializedName("category_id")
    private String categoryId;
    @Expose
    @SerializedName("brand")
    private String brand;
    @Expose
    @SerializedName("product_author")
    private String productAuthor;
    @Expose
    @SerializedName("product_descripition")
    private String productDescripition;
    @Expose
    @SerializedName("product_image")
    private String productImage;

    @Expose
    @SerializedName("product_quantity")
    private String productQuantity;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Expose
    @SerializedName("discount")
    private String discount;

    @Expose
    @SerializedName("product_price")
    private String productPrice;
    @Expose
    @SerializedName("product_name")
    private String productName;
    @Expose
    @SerializedName("product_id")
    private String productId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(String subcatId) {
        this.subcatId = subcatId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductAuthor() {
        return productAuthor;
    }

    public void setProductAuthor(String productAuthor) {
        this.productAuthor = productAuthor;
    }

    public String getProductDescripition() {
        return productDescripition;
    }

    public void setProductDescripition(String productDescripition) {
        this.productDescripition = productDescripition;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
