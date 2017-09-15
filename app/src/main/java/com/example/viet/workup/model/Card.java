package com.example.viet.workup.model;

import java.util.ArrayList;

/**
 * Created by viet on 12/09/2017.
 */

public class Card {
    private String key;

    private String coverImageUrl;
    private ArrayList<Label> arrLabel;
    private String title;
    private int commentCount;
    private int attachment;
    private String checkWork;
    private DueDate dueDate;
    private ArrayList<UserInfo> arrUserInfo;
    private boolean hasDescription;

    public Card() {

    }

    public Card(String coverImageUrl,
                ArrayList<Label> arrLabel,
                String title,
                int commentCount,
                int attachment,
                String checkWork,
                DueDate dueDate,
                ArrayList<UserInfo> arrUserInfo,
                boolean hasDescription) {
        this.coverImageUrl = coverImageUrl;
        this.arrLabel = arrLabel;
        this.title = title;
        this.commentCount = commentCount;
        this.attachment = attachment;
        this.checkWork = checkWork;
        this.dueDate = dueDate;
        this.arrUserInfo = arrUserInfo;
        this.hasDescription = hasDescription;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public ArrayList<Label> getArrLabel() {
        return arrLabel;
    }

    public void setArrLabel(ArrayList<Label> arrLabel) {
        this.arrLabel = arrLabel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getAttachment() {
        return attachment;
    }

    public void setAttachment(int attachment) {
        this.attachment = attachment;
    }

    public String getCheckWork() {
        return checkWork;
    }

    public void setCheckWork(String checkWork) {
        this.checkWork = checkWork;
    }

    public DueDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    public ArrayList<UserInfo> getArrUserInfo() {
        return arrUserInfo;
    }

    public void setArrUserInfo(ArrayList<UserInfo> arrUserInfo) {
        this.arrUserInfo = arrUserInfo;
    }

    public boolean isHasDescription() {
        return hasDescription;
    }

    public void setHasDescription(boolean hasDescription) {
        this.hasDescription = hasDescription;
    }
}
