package com.example.viet.workup.model;

/**
 * Created by viet on 14/09/2017.
 */

public class ActionAttachImage {
    private UserInfo userInfo;
    private String imageUrl;

    public ActionAttachImage() {
    }

    public ActionAttachImage(UserInfo userInfo, String imageUrl) {
        this.userInfo = userInfo;
        this.imageUrl = imageUrl;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
