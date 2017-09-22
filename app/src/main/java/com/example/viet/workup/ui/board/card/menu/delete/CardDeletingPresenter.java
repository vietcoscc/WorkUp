package com.example.viet.workup.ui.board.card.menu.delete;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardListRef;

/**
 * Created by viet on 18/09/2017.
 */

public class CardDeletingPresenter<V extends CardDeletingMvpView> extends BasePresenter<V> implements CardDeletingMvpPresenter<V> {
    public static final String TAG = "CardDeletingPresenter";

    @Inject
    public CardDeletingPresenter() {
    }

    @Override
    public void onDeleteCardList(String boardKey, String cardListKey) {
        if (TextUtils.isEmpty(boardKey) || TextUtils.isEmpty(cardListKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        cardListRef(boardKey, cardListKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (getmMvpView() == null) {
                    return;
                }
                getmMvpView().hideProgress();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (getmMvpView() == null) {
                    return;
                }
                getmMvpView().hideProgress();
            }
        });
    }
}
