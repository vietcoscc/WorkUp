package com.example.viet.workup.model;

import android.net.Uri;

/**
 * Created by viet on 06/09/2017.
 */

public class UserInfo {
    private String displayName;
    private String email;
    private String joinedDate;
    private String photoUrl;
    private String uid;

    public UserInfo() {
    }

    public UserInfo(String displayName,
                    String email,
                    String joinedDate,
                    String photoUrl,
                    String uid) {
        this.displayName = displayName;
        this.email = email;
        this.joinedDate = joinedDate;
        this.photoUrl = photoUrl;
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
