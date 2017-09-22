package com.example.viet.workup.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.viet.workup.MyApplication;
import com.example.viet.workup.di.component.ActivityComponent;

/**
 * Created by viet on 12/09/2017.
 */

public class BaseFragment extends Fragment implements MvpView {
    private ActivityComponent mActivityComponent;
    protected ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        MyApplication myApplication = (MyApplication) appCompatActivity.getApplication();
        mActivityComponent = myApplication.getmActivityComponent();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessge(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isOnProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            return true;
        }
        return false;
    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
