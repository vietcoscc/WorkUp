package com.example.viet.workup.ui.board.card.menu.delete;

import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.utils.CalendarUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardListRef;

/**
 * Created by viet on 18/09/2017.
 */

public class CardDeletingPresenter<V extends CardDeletingMvpView> extends BasePresenter<V> implements CardDeletingMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();
    public static final String TAG = "CardDeletingPresenter";

    @Inject
    public CardDeletingPresenter() {
    }

    @Override
    public void onDeleteCardList(final String boardKey, final String cardListKey, final String cardListName) {
        if (TextUtils.isEmpty(boardKey) || TextUtils.isEmpty(cardListKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        arrCardRef(boardKey, cardListKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                cardDataRef(boardKey + "+" + cardListKey + "+" + dataSnapshot.getKey()).removeValue();
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

        arrCardRef(boardKey, cardListKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cardListRef(boardKey, cardListKey).removeValue();
                arrCardRef(boardKey, cardListKey).removeValue();
                String from = mAccountManager.getCurrentUser().getDisplayName();
                String message = " deleted list  ";
                String target = cardListName;
                String timeStamp = CalendarUtils.getCurrentTime() + " " + CalendarUtils.getCurrentDate();
                arrActivityRef(boardKey).push().setValue(new BoardUserActivity(from, message, target, timeStamp,false));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
