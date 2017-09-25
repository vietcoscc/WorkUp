package com.example.viet.workup.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.example.viet.workup.R;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


/**
 * Created by viet on 15/08/2017.
 */

public class ApplicationUtils {
    private static Field field[] = R.raw.class.getFields();

    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static int rawId(int id) {
        try {
            return field[id].getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Field> getArrId() {
        return Arrays.asList(field);
    }

    public static Dialog buildConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("OK", onClickListener);
        builder.setNegativeButton("CANCEL", null);
        return builder.create();
    }

    public static TextWatcher getTextWatcher(final ImageView imageView) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    imageView.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
}
