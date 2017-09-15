package com.example.viet.workup.model;

import java.util.ArrayList;

/**
 * Created by viet on 12/09/2017.
 */

public class CardList {
    private String key;

    private String title;
    private ArrayList<Card> arrCard;

    public CardList() {
    }

    public CardList(String title, ArrayList<Card> arrCard) {
        this.title = title;
        this.arrCard = arrCard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Card> getArrCard() {
        return arrCard;
    }

    public void setArrCard(ArrayList<Card> arrCard) {
        this.arrCard = arrCard;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
