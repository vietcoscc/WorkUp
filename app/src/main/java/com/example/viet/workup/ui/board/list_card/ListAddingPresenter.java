package com.example.viet.workup.ui.board.list_card;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.utils.CalendarUtils;
import com.example.viet.workup.utils.DataUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardDataRef;

/**
 * Created by viet on 04/09/2017.
 */

public class ListAddingPresenter<V extends ListAddingMvpView> extends BasePresenter<V> implements ListAddingMvpPresenter<V> {
    public static final String TAG = "ListAddingPresenter";
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public ListAddingPresenter() {
    }

    @Override
    public void onButtonAddListClick(final String title, final String boardKey) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(boardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        if (!DataUtils.isCardTitleValid(title)) {
            if (getmMvpView() != null) {
                getmMvpView().showMessge("List title invalid");
            }
            return;
        }
        getmMvpView().showProgress();
        CardList cardList = new CardList(title);
        boardDataRef(boardKey).push().setValue(cardList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                String from = mAccountManager.getCurrentUser().getDisplayName();
                String message = " added list ";
                String target = title;
                String timeStamp = CalendarUtils.getCurrentTime() + " " + CalendarUtils.getCurrentDate();
                arrActivityRef(boardKey).push().setValue(new BoardUserActivity(from, message, target, timeStamp,false));
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
