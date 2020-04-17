package com.kplar.models;

import com.google.gson.annotations.SerializedName;

public class UserResponseData {
    @SerializedName("id")
    String id;

    @SerializedName("response")
    String responseCode;

    @SerializedName("name")
    String userName;

    @SerializedName("mobile")
    String mobile;

    @SerializedName("password")
    String pwd;

    public UserResponseData(String id, String responseCode, String userName, String mobile, String pwd) {
        this.id = id;
        this.responseCode = responseCode;
        this.userName = userName;
        this.mobile = mobile;
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
