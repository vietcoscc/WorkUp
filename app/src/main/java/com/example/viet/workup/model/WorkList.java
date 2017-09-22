package com.example.viet.workup.model;

import java.util.ArrayList;

/**
 * Created by viet on 13/09/2017.
 */

public class WorkList {
    private String title;
    private int doneCount = 0;
    private ArrayList<Task> arrTask;

    public WorkList() {
    }

    public WorkList(String title, int doneCount, ArrayList<Task> arrTask) {
        this.title = title;
        this.doneCount = doneCount;
        this.arrTask = arrTask;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
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
