package com.example.viet.workup.ui.board.background;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.utils.CalendarUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardImageUrlRef;

/**
 * Created by viet on 15/09/2017.
 */

public class BackgroundPresenter<V extends BackgroundMvpView> extends BasePresenter<V> implements BackgroundMvpPresenter<V> {
    private static final String TAG = "BackgroundPresenter";
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public BackgroundPresenter() {
    }


    @Override
    public void onUpdateBackground(boolean isStar, String uid, final String boardKey, int position) {
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(boardKey)) {
            return;
        }
        getmMvpView().showProgress();
        boardImageUrlRef(isStar, uid, boardKey)
                .setValue(position)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (getmMvpView() == null) {
                            return;
                        }
                        String from = mAccountManager.getCurrentUser().getDisplayName();
                        String message = " updated background ";
                        String target = "";
                        String timeStamp = CalendarUtils.getCurrentTime() + " " + CalendarUtils.getCurrentDate();
                        arrActivityRef(boardKey).push().setValue(new BoardUserActivity(from, message, target, timeStamp, false));
                        getmMvpView().hideProgress();
                        getmMvpView().showMessge("onSuccess");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (getmMvpView() == null) {
                            return;
                        }
                        getmMvpView().hideProgress();
                        getmMvpView().showMessge("onFailure");
                    }
                });
    }
}
