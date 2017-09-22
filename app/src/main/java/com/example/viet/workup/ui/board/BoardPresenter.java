package com.example.viet.workup.ui.board;

import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.utils.ApplicationUtils;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardImageUrlRef;

/**
 * Created by viet on 02/09/2017.
 */

public class BoardPresenter<V extends BoardMvpView> extends BasePresenter<V> implements BoardMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();
    private static final String TAG = "BoardPresenter";

    @Inject
    public BoardPresenter() {
    }

    @Override
    public void onReceiveData(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        FireBaseDatabaseUtils.boardDataRef(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("member")) {
                    return;
                }
                CardList cardList = dataSnapshot.getValue(CardList.class);
                cardList.setKey(dataSnapshot.getKey());
                if (getmMvpView() != null) {
                    getmMvpView().showArrCardList(cardList);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, dataSnapshot.getKey());
                String cardListKey = dataSnapshot.getKey();
                if (getmMvpView() != null) {
                    getmMvpView().hideCardList(cardListKey);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onReceiveBackground(String uid, String boardKey, boolean isStar) {
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(boardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        boardImageUrlRef(isStar, uid, boardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer id = dataSnapshot.getValue(Integer.class);
                Log.i(TAG, id + "");
                if (getmMvpView() != null) {
                    getmMvpView().showBackground(ApplicationUtils.rawId(id));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
