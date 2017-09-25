package com.example.viet.workup.model;

/**
 * Created by viet on 24/09/2017.
 */

public class BoardUserActivity {
    String from;
    String message;
    String target;
    String timeStamp;

    public BoardUserActivity() {
    }

    public BoardUserActivity(String from, String message, String target, String timeStamp) {
        this.from = from;
        this.message = message;
        this.target = target;
        this.timeStamp = timeStamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
