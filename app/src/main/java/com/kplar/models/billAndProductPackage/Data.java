package com.kplar.models.billAndProductPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @Expose
    @SerializedName("subTotal")
    private double subtotal;
    @Expose
    @SerializedName("coupon")
    private int coupon;
    @Expose
    @SerializedName("shippingCharge")
    private int shippingcharge;
    @Expose
    @SerializedName("quantity")
    private String quantity;
    @Expose
    @SerializedName("discount")
    private String discount;
    @Expose
    @SerializedName("mrp")
    private String mrp;
    @Expose
    @SerializedName("productName")
    private String productname;

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public int getShippingcharge() {
        return shippingcharge;
    }

    public void setShippingcharge(int shippingcharge) {
        this.shippingcharge = shippingcharge;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
