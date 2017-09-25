package com.example.viet.workup.model;

/**
 * Created by viet on 24/09/2017.
 */

public class BoardUserActivity {
    String from;
    String message;
    String target;
    String timeStamp;
    boolean isNotified;

    public BoardUserActivity() {
    }

    public BoardUserActivity(String from, String message, String target, String timeStamp, boolean isNotified) {
        this.from = from;
        this.message = message;
        this.target = target;
        this.timeStamp = timeStamp;
        this.isNotified = isNotified;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
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
