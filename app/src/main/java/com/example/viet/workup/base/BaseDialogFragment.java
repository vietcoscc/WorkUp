package com.example.viet.workup.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.example.viet.workup.di.component.ActivityComponent;
import com.example.viet.workup.di.component.DaggerActivityComponent;
import com.example.viet.workup.di.module.ActivityModule;

/**
 * Created by viet on 07/09/2017.
 */

public class BaseDialogFragment extends DialogFragment implements MvpView {
    private ActivityComponent mActivityComponent;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(getContext());
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule()).build();
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

    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
