package com.example.viet.workup.utils;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.viet.workup.R;
import com.example.viet.workup.model.BoardUserActivity;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by viet on 15/08/2017.
 */

public class ApplicationUtils {
    private static Field field[] = R.raw.class.getFields();
    private static boolean inApp = false;

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

    public static Dialog buildInputDialog(Context context, String message, DialogInterface.OnClickListener onClickListener, EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(editText);
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


    public static void showNotification(Context context, BoardUserActivity boardUserActivity) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(boardUserActivity.getTimeStamp());
        builder.setContentText(boardUserActivity.getFrom() + "" + boardUserActivity.getMessage() + "" + boardUserActivity.getTarget());
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_board);
        builder.setLargeIcon(drawable.getBitmap());
        builder.setSmallIcon(R.drawable.ic_board);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), notification);
    }

    public static boolean isInApp() {
        return inApp;
    }

    public static void setInApp(boolean inApp) {
        ApplicationUtils.inApp = inApp;
    }

    public static void picasso(Context context, String url, ImageView iv) {
        if (TextUtils.isEmpty(url) || url.equals("null")) {
            Picasso.with(context)
                    .load(R.drawable.man)
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(iv);
        } else {
            Picasso.with(context)
                    .load(url)
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(iv);
        }
    }

    public static void glide(Context context, String url, ImageView iv) {
        if (TextUtils.isEmpty(url) || url.equals("null")) {
            Glide.with(context)
                    .load(R.drawable.man)
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(iv);
        } else {
            Glide.with(context)
                    .load(url)
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(iv);
        }
    }
}
