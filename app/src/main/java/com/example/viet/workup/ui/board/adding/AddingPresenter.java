package com.example.viet.workup.ui.board.adding;

import android.support.annotation.NonNull;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

/**
 * Created by viet on 04/09/2017.
 */

public class AddingPresenter<V extends AddingMvpView> extends BasePresenter<V> implements AddingMvpPresenter<V> {
    @Inject
    public AddingPresenter() {
    }

    @Override
    public void onButtonAddListClick(String title, String boardKey) {
        getmMvpView().showProgress();
        CardList cardList = new CardList(title, null);
        FireBaseDatabaseUtils.boardDataRef(boardKey).push().setValue(cardList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                }
            }
        });
    }
}
