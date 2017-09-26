package com.example.viet.workup.ui.board.card;

import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.CardDetail;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.Task;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.model.WorkList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardListRef;

/**
 * Created by viet on 04/09/2017.
 */

public class CardPresenter<V extends CardMvpView> extends BasePresenter<V> implements CardMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();
    private static final String TAG = "CardListPresenter";

    @Inject
    public CardPresenter() {

    }

    @Override
    public void onReceiveTitle(String boardKey, String cardListKey) {
        cardListRef(boardKey, cardListKey).child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.getValue(String.class);
                if (!TextUtils.isEmpty(title) && getmMvpView() != null) {
                    getmMvpView().showListTitle(title);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChangeTitle(String boardKey, String cardListKey, String title) {
        cardListRef(boardKey, cardListKey).child("title").setValue(title.trim());
    }

    @Override
    public void onReceiveData(final String boardKey, final String cardListKey) {
        if (TextUtils.isEmpty(boardKey) || TextUtils.isEmpty(cardListKey)) {
            Log.e(TAG, "Empty!");
            return;
        }

        arrCardRef(boardKey, cardListKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("arrLabel")) {
                    return;
                }
                final Card card = dataSnapshot.getValue(Card.class);
                card.setBoardKey(boardKey);
                card.setCardListKey(cardListKey);
                card.setKey(dataSnapshot.getKey());
                if (getmMvpView() != null) {
                    getmMvpView().showCard(card);
                }
                cardDataRef(boardKey + "+" + cardListKey + "+" + card.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            CardDetail cardDetail = generateCardDetail(card, card.getDescription());
                            cardDataRef(dataSnapshot.getKey()).setValue(cardDetail);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Log.i(TAG, dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Card card = dataSnapshot.getValue(Card.class);
                card.setBoardKey(boardKey);
                card.setCardListKey(cardListKey);
                card.setKey(dataSnapshot.getKey());
                if (getmMvpView() != null) {
                    getmMvpView().changeCard(card);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Card card = dataSnapshot.getValue(Card.class);
                card.setBoardKey(boardKey);
                card.setCardListKey(cardListKey);
                card.setKey(dataSnapshot.getKey());
                if (getmMvpView() != null) {
                    getmMvpView().removeCard(card);
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
    public void onCardClick(String boardKey, String cardListKey, String position) {
        if (TextUtils.isEmpty(boardKey) || TextUtils.isEmpty(cardListKey) || TextUtils.isEmpty(position)) {
            Log.e(TAG, "Empty!");
            return;
        }
        String cardKey = boardKey.trim() + "+" + cardListKey.trim() + "+" + position;
        if (getmMvpView() == null) {
            return;
        }
        getmMvpView().showCardDetail(cardKey);
    }

    private CardDetail generateCardDetail(Card card, String description) {
        ArrayList<WorkList> arrWorkList = new ArrayList<>();
        ArrayList<Comment> arrComment = new ArrayList<>();
        return new CardDetail(description, arrWorkList, arrComment);
    }
}
