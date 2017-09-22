package com.example.viet.workup.ui.work.due_date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.viet.workup.MyApplication;
import com.example.viet.workup.di.component.ActivityComponent;
import com.example.viet.workup.model.DueDate;

import javax.inject.Inject;

/**
 * Created by viet on 15/09/2017.
 */

public class DueDateDialog extends DatePickerDialog implements DueDateMvpView {
    private Context mContext;
    private String mCardKey;
    @Inject
    DueDatePresenter<DueDateMvpView> mPresenter;

    public DueDateDialog(@NonNull Context context, int year, int month, int dayOfMonth, String cardKey) {
        super(context, null, year, month, dayOfMonth);
        this.mContext = context;
        this.mCardKey = cardKey;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity) mContext;
        MyApplication myApplication = (MyApplication) appCompatActivity.getApplication();
        ActivityComponent component = myApplication.getmActivityComponent();
        component.inject(this);

        mPresenter.onAttach(this);
        setButton(DialogInterface.BUTTON1, "Do", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year = getDatePicker().getYear();
                int month = getDatePicker().getMonth();
                int day = getDatePicker().getDayOfMonth();
                mPresenter.onButtonDoneClick(mCardKey, new DueDate(day, month, year));
            }
        });
    }


    @Override
    public void showProgress() {
        Toast.makeText(mContext, "Updating ... ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        dismiss();
    }

    @Override
    public void showMessge(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isOnProgress() {
        if (isShowing()) {
            return true;
        }
        return false;
    }

}
