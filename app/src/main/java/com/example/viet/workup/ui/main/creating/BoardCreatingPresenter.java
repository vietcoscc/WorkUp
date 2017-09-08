package com.example.viet.workup.ui.main.creating;

import android.support.annotation.NonNull;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.starBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.unstarBoardRef;

/**
 * Created by viet on 07/09/2017.
 */

public class BoardCreatingPresenter<V extends BoardCreatingMvpView> extends BasePresenter<V> implements BoardCreatingMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public BoardCreatingPresenter() {

    }

    @Override
    public void onCreateBoardButtonClick(String name, String group, boolean isChecked) {
        getmMvpView().showProgress();
        Board board = new Board(name, "", isChecked);
        DatabaseReference ref;
        if (isChecked) {
            ref = starBoardRef(mAccountManager.getmAuth().getCurrentUser().getUid());
        } else {
            ref = unstarBoardRef(mAccountManager.getmAuth().getCurrentUser().getUid());
        }
        ref.push().setValue(board).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                    getmMvpView().showMessge("onSuccess");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                    getmMvpView().showMessge("onFailure");
                }
            }
        });
    }
}
