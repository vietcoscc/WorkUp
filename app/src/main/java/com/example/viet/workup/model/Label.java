package com.example.viet.workup.model;

import android.graphics.Color;

/**
 * Created by viet on 12/09/2017.
 */

public class Label {
    private String key;
    private String color;
    private String text;

    public Label() {
    }

    public Label(String color, String text) {
        this.color = color;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
