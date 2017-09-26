package com.example.viet.workup.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.viet.workup.MyApplication;
import com.example.viet.workup.di.component.ActivityComponent;

/**
 * Created by viet on 08/09/2017.
 */

public class BasePopupMenu extends PopupMenu implements MvpView {
    protected Context mContext;
    private ActivityComponent mActivityComponent;
    protected ProgressDialog mProgressDialog;

    public BasePopupMenu(Context context, View anchor) {
        super(context, anchor);
        this.mContext = context;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        AppCompatActivity appCompatActivity = (AppCompatActivity) mContext;
        MyApplication myApplication = (MyApplication) appCompatActivity.getApplication();
        mActivityComponent = myApplication.getActivityComponent();
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showProgress() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
    }

    @Override
    public void showMessge(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isOnProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return true;
        }
        return false;
    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
