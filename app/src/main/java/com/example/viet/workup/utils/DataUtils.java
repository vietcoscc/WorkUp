package com.example.viet.workup.utils;

import android.text.TextUtils;

/**
 * Created by viet on 18/09/2017.
 */

public class DataUtils {
    public static boolean isBoardNameValid(String name) {
        int length = name.length();
        if (!TextUtils.isEmpty(name) && length >= 5 && length <= 30) {
            return true;
        }
        return false;
    }

    public static boolean isCardListNameValid(String name) {
        int length = name.length();
        if (!TextUtils.isEmpty(name) && length >= 3 && length <= 40) {
            return true;
        }
        return false;
    }


    public static boolean isWorkListNameValid(String name) {
        int length = name.length();
        if (!TextUtils.isEmpty(name) && length >= 5 && length <= 30) {
            return true;
        }
        return false;
    }

    public static boolean isCardTitleValid(String name) {
        int length = name.length();
        if (!TextUtils.isEmpty(name) && length >= 5 && length <= 60) {
            return true;
        }
        return false;
    }

    public static boolean isCardDescriptionValid(String name) {
        int length = name.length();
        if (!TextUtils.isEmpty(name) && length >= 1 && length <= 500) {
            return true;
        }
        return false;
    }

    public static boolean isDisplayNameValid(String displayName) {
        int length = displayName.length();
        if (!TextUtils.isEmpty(displayName) && length >= 4 && length <= 20) {
            return true;
        }
        return false;
    }
}
