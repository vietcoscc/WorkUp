package com.example.viet.workup.ui.work.label;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.Label;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.labelCardRef;

/**
 * Created by viet on 15/09/2017.
 */

public class LabelPresenter<V extends LabelMvpView> extends BasePresenter<V> implements LabelMvpPresenter<V> {
    public static final String TAG = "LabelPresenter";

    @Inject
    public LabelPresenter() {
    }

    @Override
    public void onAddLabel(final String cardKey, final String color, final String label) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        getmMvpView().showProgress();
        final Label lb = new Label(color, label);
        labelCardRef(cardKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                labelCardRef(cardKey).child(count + "").setValue(lb).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (getmMvpView() != null) {
                            getmMvpView().showSuccess();
                            getmMvpView().hideProgress();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (getmMvpView() != null) {
                            getmMvpView().showFailure();
                            getmMvpView().hideProgress();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
