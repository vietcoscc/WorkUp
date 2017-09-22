package com.example.viet.workup.ui.work.due_date;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

/**
 * Created by viet on 15/09/2017.
 */

public class DueDatePresenter<V extends DueDateMvpView> extends BasePresenter<V> implements DueDateMvpPresenter<V> {
    public static final String TAG = "DueDatePresenter";
    @Inject
    public DueDatePresenter() {
    }

    @Override
    public void onButtonDoneClick(String mKey, DueDate dueDate) {
        if(TextUtils.isEmpty(mKey)){
            Log.e(TAG,"Empty!");
            return;
        }
        getmMvpView().showProgress();
        FireBaseDatabaseUtils.dueDateCardRef(mKey).setValue(dueDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getmMvpView().hideProgress();
                getmMvpView().showMessge("onSuccess");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getmMvpView().hideProgress();
                getmMvpView().showMessge("onFailure");
            }
        });
    }
}
