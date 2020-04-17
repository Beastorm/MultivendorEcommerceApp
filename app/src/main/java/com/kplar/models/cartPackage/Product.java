package com.kplar.models.cartPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @Expose
    @SerializedName("quantity")
    private String quantity;
    @Expose
    @SerializedName("date")
    private String date;
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
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("color")
    private String color;
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
    @SerializedName("stock")
    private String stock;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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
