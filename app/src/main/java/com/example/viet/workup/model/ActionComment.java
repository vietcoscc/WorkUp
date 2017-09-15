package com.example.viet.workup.model;

/**
 * Created by viet on 13/09/2017.
 */

public class ActionComment {
    private UserInfo userInfo;
    private String content;

    public ActionComment() {
    }

    public ActionComment(UserInfo userInfo, String content) {
        this.userInfo = userInfo;
        this.content = content;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
