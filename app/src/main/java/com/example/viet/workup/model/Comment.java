package com.example.viet.workup.model;

/**
 * Created by viet on 14/09/2017.
 */

public class Comment {
    private UserInfo userInfo;
    private String content;
    private String timeStamp;

    public Comment() {
    }

    public Comment(UserInfo userInfo, String content, String timeStamp) {
        this.userInfo = userInfo;
        this.content = content;
        this.timeStamp = timeStamp;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
