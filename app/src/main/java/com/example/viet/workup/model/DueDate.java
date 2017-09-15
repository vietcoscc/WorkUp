package com.example.viet.workup.model;

/**
 * Created by viet on 12/09/2017.
 */

public class DueDate {
    private int day;
    private int month;
    private int yeah;

    public DueDate() {
    }

    public DueDate(int day, int month, int yeah) {
        this.day = day;
        this.month = month;
        this.yeah = yeah;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYeah() {
        return yeah;
    }

    public void setYeah(int yeah) {
        this.yeah = yeah;
    }
}
