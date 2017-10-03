package com.example.viet.workup.model;

/**
 * Created by viet on 14/09/2017.
 */

public class Comment {
    private String uid;
    private String content;
    private String timeStamp;

    public Comment() {
    }

    public Comment(String uid, String content, String timeStamp) {
        this.uid = uid;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
