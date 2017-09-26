package com.example.viet.workup.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.viet.workup.MyApplication;
import com.example.viet.workup.R;
import com.example.viet.workup.di.component.ActivityComponent;

import butterknife.BindView;

/**
 * Created by viet on 07/09/2017.
 */

public class BaseDialogFragment extends DialogFragment implements MvpView {
    private ActivityComponent mActivityComponent;
    protected AlertDialog mDialog;
    protected Context mContext;

    @BindView(R.id.progressBar)
    protected
    ContentLoadingProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        MyApplication myApplication = (MyApplication) appCompatActivity.getApplication();
        mActivityComponent = myApplication.getActivityComponent();
        mContext = getContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Cancel", null);
        mDialog = builder.create();
        mDialog.setCancelable(false);
        return mDialog;
    }

    @Override
    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.hide();
            mProgressBar.setVisibility(View.GONE);

        }
        mDialog.dismiss();
    }

    @Override
    public void showMessge(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isOnProgress() {
        if (mProgressBar != null && mProgressBar.isShown()) {
            return true;
        }
        return false;
    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }
}
