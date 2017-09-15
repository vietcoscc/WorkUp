package com.example.viet.workup.model;

/**
 * Created by viet on 13/09/2017.
 */

public class ActionAttachFile {
    private UserInfo userInfo;
    private String fileUrl;

    public ActionAttachFile() {
    }

    public ActionAttachFile(UserInfo userInfo, String fileUrl) {
        this.userInfo = userInfo;
        this.fileUrl = fileUrl;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
