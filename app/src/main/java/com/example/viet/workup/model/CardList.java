package com.example.viet.workup.model;

import java.io.Serializable;

/**
 * Created by viet on 12/09/2017.
 */

public class CardList {
    private String key;
    private String title;

    public CardList() {
    }

    public CardList(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
