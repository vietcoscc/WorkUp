package com.example.viet.workup.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.viet.workup.MyApplication;
import com.example.viet.workup.di.component.ActivityComponent;

/**
 * Created by viet on 02/09/2017.
 */

public class BaseActivity extends AppCompatActivity implements MvpView {
    private ActivityComponent mActivityComponent;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        hideProgress();
        MyApplication myApplication = (MyApplication) getApplication();
        mActivityComponent = myApplication.getmActivityComponent();

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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isOnProgress() {
        if (progressDialog != null &&progressDialog.isShowing()) {
            return true;
        }
        return false;
    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
