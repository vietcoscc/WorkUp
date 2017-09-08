package com.example.viet.workup.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.viet.workup.di.component.ActivityComponent;
import com.example.viet.workup.di.component.DaggerActivityComponent;
import com.example.viet.workup.di.module.ActivityModule;

/**
 * Created by viet on 02/09/2017.
 */

public class BaseActivity extends AppCompatActivity implements MvpView {
    private ActivityComponent mActivityComponent;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule()).build();
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

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
