package com.example.viet.workup.ui.board.card;

import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.CardList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardListRef;

/**
 * Created by viet on 04/09/2017.
 */

public class CardPresenter<V extends CardMvpView> extends BasePresenter<V> implements CardMvpPresenter<V> {
    private static final String TAG = "CardListPresenter";

    @Inject
    public CardPresenter() {

    }

    @Override
    public void onReceiveData(final String boardKey, final String cardListKey) {
        cardListRef(boardKey, cardListKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CardList cardList = dataSnapshot.getValue(CardList.class);
                if (getmMvpView() != null) {
                    getmMvpView().showListTitle(cardList.getTitle());
                }
                arrCardRef(boardKey, cardListKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Card card = dataSnapshot.getValue(Card.class);
                        card.setKey(dataSnapshot.getKey());
                        Log.i(TAG, dataSnapshot.getKey());
                        if (getmMvpView() != null) {
                            getmMvpView().showCard(card);
                        }
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCardClick(String boardKey, String cardListKey, String position) {
        String cardKey = boardKey.trim() + "+" + cardListKey.trim() + "+" + position;
        getmMvpView().showCardDetail(cardKey);
    }


}
