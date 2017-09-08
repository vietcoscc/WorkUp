package com.example.viet.workup.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by viet on 15/08/2017.
 */

public class AccountUtils {
    public static ProgressDialog buildProgressDialog(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        return dialog;
    }

    public static boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}"
        );
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}
