package com.example.viet.workup.model;

import java.util.ArrayList;

/**
 * Created by viet on 13/09/2017.
 */

public class CardDetail {
    private String description;
    private ArrayList<WorkList> arrWorkList;
    private ArrayList<Comment> arrComment;

    public CardDetail() {
    }

    public CardDetail(String description,
                      ArrayList<WorkList> arrWorkList,
                      ArrayList<Comment> arrComment) {

        this.description = description;
        this.arrWorkList = arrWorkList;
        this.arrComment = arrComment;
    }

    public ArrayList<Comment> getArrComment() {
        return arrComment;
    }

    public void setArrComment(ArrayList<Comment> arrComment) {
        this.arrComment = arrComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<WorkList> getArrWorkList() {
        return arrWorkList;
    }

    public void setArrWorkList(ArrayList<WorkList> arrWorkList) {
        this.arrWorkList = arrWorkList;
    }

}

