package com.example.viet.workup.ui.board;

import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.ui.board.adding.AddingDialogFragment;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import javax.inject.Inject;

/**
 * Created by viet on 02/09/2017.
 */

public class BoardPresenter<V extends BoardMvpView> extends BasePresenter<V> implements BoardMvpPresenter<V> {

    private static final String TAG = "BoardPresenter";

    @Inject
    public BoardPresenter() {
    }

    @Override
    public void onReceiveData(String key) {
        FireBaseDatabaseUtils.boardDataRef(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CardList cardList = dataSnapshot.getValue(CardList.class);
                cardList.setKey(dataSnapshot.getKey());
                if (getmMvpView() != null) {
                    getmMvpView().showArrCardList(cardList);
                }
                Log.i(TAG, cardList.getTitle());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
    public void onFlatingActionButtonClick() {
        getmMvpView().showAddingListDialog();

    }
}
