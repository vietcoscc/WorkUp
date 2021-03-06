package com.example.viet.workup.ui.main.board;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.example.viet.workup.utils.DataUtils;
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
    public static final String TAG = "BoardCreatingPresenter";
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public BoardCreatingPresenter() {

    }

    @Override
    public void onCreateBoardButtonClick(String name, String group, boolean isChecked) {
        if (!DataUtils.isBoardNameValid(name)) {
            if (getmMvpView() != null) {
                getmMvpView().showMessge("Board name invalid");
            }
            return;
        }
        if (TextUtils.isEmpty(group)) {
            Log.e(TAG, "Empty!");
            return;
        }
        getmMvpView().showProgress();
        Board board = new Board(name.trim(), 0, isChecked);
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
