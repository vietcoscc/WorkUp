package com.example.viet.workup.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by viet on 06/09/2017.
 */

public class CalendarUtils {
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public static String getCurrentTime() {
        return getHour() + ":" + getMinute() + ":" + getSecond();
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getCurrentDate() {
        return getDay() + "-" + getMonth() + "-" + getYear();
    }

    public static Date getCurrentDay() {
        return new Date(getYear(), getMonth(), getDay());
    }
}
