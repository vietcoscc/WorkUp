package com.example.viet.workup.ui.board.card.menu.move;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.CardDetail;
import com.example.viet.workup.model.CardList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.starBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.unstarBoardRef;

/**
 * Created by viet on 17/09/2017.
 */

public class CardMovingPresenter<V extends CardMovingMvpView> extends BasePresenter<V> implements CardMovingMvpPresenter<V> {
    public static final String TAG = "CardMovingPresenter";
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public CardMovingPresenter() {
    }

    @Override
    public void onReceiveBoardList(final String thisBoard) {
        if (TextUtils.isEmpty(thisBoard)) {
            Log.e(TAG, thisBoard);
            return;
        }
        starBoardRef(mAccountManager.getmAuth().getCurrentUser().getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().equals(thisBoard)) {
                            return;
                        }
                        Board board = dataSnapshot.getValue(Board.class);
                        board.setKey(dataSnapshot.getKey());
                        if (getmMvpView() != null) {
                            getmMvpView().showBoard(board);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                        Log.i(TAG, s);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Log.i(TAG, s);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        unstarBoardRef(mAccountManager.getmAuth().getCurrentUser().getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().equals(thisBoard)) {
                            return;
                        }
                        Board board = dataSnapshot.getValue(Board.class);
                        board.setKey(dataSnapshot.getKey());
                        if (getmMvpView() != null) {
                            getmMvpView().showBoard(board);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                        Log.i(TAG, s);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Log.i(TAG, s);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onRemoveList(final String srcBoardKey, final String cardListKey, final String desBoardKey) {
        if (TextUtils.isEmpty(srcBoardKey) || TextUtils.isEmpty(cardListKey) || TextUtils.isEmpty(desBoardKey)) {
            Log.e(TAG, "Empty|");
            return;
        }
//        getmMvpView().showProgress();
//        cardListRef(srcBoardKey, cardListKey).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                CardList cardList = dataSnapshot.getValue(CardList.class);
//                if (cardList == null) {
//                    return;
//                }
//                ArrayList<Card> cards = cardList.getArrCard();
//                if(cards != null){
//                    for (int i = 0; i < cards.size(); i++) {
//                        final int position = i;
//                        cardDataRef(srcBoardKey + "+" + cardListKey + "+" + i).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                CardDetail cardDetail = dataSnapshot.getValue(CardDetail.class);
//
//                                cardDataRef(desBoardKey + "+" + cardListKey + "+" + position).setValue(cardDetail);
//                                cardDataRef(srcBoardKey + "+" + cardListKey + "+" + position).removeValue();
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//                }
//
//                boardDataRef(desBoardKey).child(cardListKey).setValue(cardList).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        cardListRef(srcBoardKey, cardListKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                if (getmMvpView() == null) {
//                                    return;
//                                }
//                                getmMvpView().hideProgress();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                if (getmMvpView() == null) {
//                                    return;
//                                }
//                                getmMvpView().hideProgress();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        cardListRef(desBoardKey, cardListKey).removeValue();
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
