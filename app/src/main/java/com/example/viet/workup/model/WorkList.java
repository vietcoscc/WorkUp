package com.example.viet.workup.model;

import java.util.ArrayList;

/**
 * Created by viet on 13/09/2017.
 */

public class WorkList {
    private String title;
    private ArrayList<Task> arrTask;

    public WorkList() {
    }

    public WorkList(String title, ArrayList<Task> arrTask) {
        this.title = title;
        this.arrTask = arrTask;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getArrTask() {
        return arrTask;
    }

    public void setArrTask(ArrayList<Task> arrTask) {
        this.arrTask = arrTask;
    }
}
