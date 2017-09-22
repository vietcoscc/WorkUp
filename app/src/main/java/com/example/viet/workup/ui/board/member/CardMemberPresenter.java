package com.example.viet.workup.ui.board.member;

import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.memberCardRef;

/**
 * Created by viet on 15/09/2017.
 */

public class CardMemberPresenter<V extends CardMemberMvpView> extends BasePresenter<V> implements CardMemberMvpPresenter<V> {
    private static final String TAG = "BackgroundPresenter";

    @Inject
    public CardMemberPresenter() {
    }

    @Override
    public void onReceiveMemeber(final String cardKey) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        memberCardRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                        if (getmMvpView() != null) {
                            getmMvpView().showMember(userInfo);
                        }
                    }
                }.start();

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
        memberCardRef(cardKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onReceiveBoardMember(cardKey.split("\\+")[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onReceiveBoardMember(final String boardKey) {
        if (TextUtils.isEmpty(boardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        boardDataRef(boardKey).child("member").addChildEventListener(childEventListener);
        boardDataRef(boardKey).child("member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boardDataRef(boardKey).child("member").removeEventListener(childEventListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
            UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
            if (getmMvpView() == null) {
                return;
            }
            getmMvpView().showBoardMember(userInfo);
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
    };
}
