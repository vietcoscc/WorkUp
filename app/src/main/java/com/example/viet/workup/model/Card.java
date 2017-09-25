package com.example.viet.workup.model;

import java.util.ArrayList;

/**
 * Created by viet on 12/09/2017.
 */

public class Card {
    private String boardKey;
    private String cardListKey;
    private String key;

    private String coverImageUrl;
    private String title;
    private int commentCount;
    private String checkWork;
    private DueDate dueDate;
    private ArrayList<Label> arrLabel;
    private ArrayList<UserInfo> arrUserInfo;
    private boolean hasDescription;
    private String description;

    public Card() {

    }

    public Card(String coverImageUrl,
                String title,
                int commentCount,
                String checkWork,
                DueDate dueDate,
                ArrayList<Label> arrLabel,
                ArrayList<UserInfo> arrUserInfo,
                boolean hasDescription, String description) {
        this.coverImageUrl = coverImageUrl;
        this.title = title;
        this.commentCount = commentCount;
        this.checkWork = checkWork;
        this.dueDate = dueDate;
        this.arrLabel = arrLabel;
        this.arrUserInfo = arrUserInfo;
        this.hasDescription = hasDescription;
        this.description = description;
    }

    public String getBoardKey() {
        return boardKey;
    }

    public void setBoardKey(String boardKey) {
        this.boardKey = boardKey;
    }

    public String getCardListKey() {
        return cardListKey;
    }

    public void setCardListKey(String cardListKey) {
        this.cardListKey = cardListKey;
    }

    public ArrayList<Label> getArrLabel() {
        return arrLabel;
    }

    public void setArrLabel(ArrayList<Label> arrLabel) {
        this.arrLabel = arrLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCardKey() {
        return boardKey + "+" + cardListKey + "+" + key;
    }
}
