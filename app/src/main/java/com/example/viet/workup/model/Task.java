package com.example.viet.workup.model;

/**
 * Created by viet on 13/09/2017.
 */

public class Task {
    private String key;
    private String task;
    private boolean hasDone;

    public Task() {
    }

    public Task(String task, boolean hasDone) {
        this.task = task;
        this.hasDone = hasDone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isHasDone() {
        return hasDone;
    }

    public void setHasDone(boolean hasDone) {
        this.hasDone = hasDone;
    }
}
