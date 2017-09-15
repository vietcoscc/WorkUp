package com.example.viet.workup.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.di.component.ActivityComponent;
import com.example.viet.workup.di.component.DaggerActivityComponent;
import com.example.viet.workup.di.module.ActivityModule;

import butterknife.BindView;

/**
 * Created by viet on 07/09/2017.
 */

public class BaseDialogFragment extends DialogFragment implements MvpView {
    private ActivityComponent mActivityComponent;
    protected AlertDialog mDialog;
    protected Context mContext;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule()).build();
        mContext = getContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Cancel", null);
        mDialog = builder.create();
        return mDialog;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mDialog.dismiss();
    }

    @Override
    public void showMessge(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
