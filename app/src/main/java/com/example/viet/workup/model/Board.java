package com.example.viet.workup.model;

/**
 * Created by viet on 06/09/2017.
 */

public class Board {
    private String key;
    private String title;
    private String imageUrl;
    private boolean isStar;

    public Board() {
    }

    public Board(String title, String imageUrl, boolean isStar) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.isStar = isStar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }
}
