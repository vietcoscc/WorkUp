package com.example.viet.workup.ui.work.work_list;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.WorkList;
import com.example.viet.workup.utils.DataUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrWorkListRef;

/**
 * Created by viet on 15/09/2017.
 */

public class WorkListPresenter<V extends WorkListMvpView> extends BasePresenter<V> implements WorkListMvpPresenter<V> {
    public static final String TAG = "WorkListPresenter";

    @Inject
    public WorkListPresenter() {
    }

    @Override
    public void onAddWorkList(final String cardKey, final String title) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        if (!DataUtils.isWorkListNameValid(title)) {
            if (getmMvpView() != null) {
                getmMvpView().showMessge("Title invalid");
            }
            return;
        }
        getmMvpView().showProgress();
        arrWorkListRef(cardKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                WorkList workList = new WorkList(title, 0, null);
                arrWorkListRef(cardKey).child(count + "").setValue(workList)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (getmMvpView() != null) {
                                    getmMvpView().hideProgress();
                                    getmMvpView().showSuccess();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (getmMvpView() != null) {
                                    getmMvpView().hideProgress();
                                    getmMvpView().showFailure();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                    getmMvpView().showFailure();
                }
            }
        });
    }
}
