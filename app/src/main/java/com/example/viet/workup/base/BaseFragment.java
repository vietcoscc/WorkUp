package com.example.viet.workup.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.viet.workup.MyApplication;
import com.example.viet.workup.di.component.ActivityComponent;

/**
 * Created by viet on 12/09/2017.
 */

public class BaseFragment extends Fragment implements MvpView {
    private ActivityComponent mActivityComponent;
    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        MyApplication myApplication = (MyApplication) appCompatActivity.getApplication();
        mActivityComponent = myApplication.getActivityComponent();
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMessge(String message) {
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
