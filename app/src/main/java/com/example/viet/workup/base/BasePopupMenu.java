package com.example.viet.workup.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.viet.workup.di.component.ActivityComponent;
import com.example.viet.workup.di.component.DaggerActivityComponent;
import com.example.viet.workup.di.module.ActivityModule;

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
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule()).build();
        mProgressDialog = new ProgressDialog(context);
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showMessge(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
