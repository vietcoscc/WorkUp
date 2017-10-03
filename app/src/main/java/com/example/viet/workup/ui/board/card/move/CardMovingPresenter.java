package com.example.viet.workup.ui.board.card.move;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.Task;
import com.example.viet.workup.model.WorkList;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCommentListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrTaskListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrWorkListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.descriptionCardRef;

/**
 * Created by viet on 01/10/2017.
 */

public class CardMovingPresenter<V extends CardMovingMvpView> extends BasePresenter<V> implements CardMovingMvpPresenter<V> {
    @Inject
    public CardMovingPresenter() {
    }

    @Override
    public void onReceiveCardList(String boardKey, String cardListKey) {
        FireBaseDatabaseUtils.boardDataRef(boardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("member")) {
                    return;
                }
                if (dataSnapshot.getKey().equals("arrCard")) {
                    return;
                }
                if (dataSnapshot.getKey().equals("arrActivity")) {
                    return;
                }
                CardList cardList = dataSnapshot.getValue(CardList.class);
                cardList.setKey(dataSnapshot.getKey());
                if (getmMvpView() != null) {
                    getmMvpView().showCardList(cardList);
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
    public void onMoveCard(final String boardKey, final String cardListKey, final String cardKey, final String desCardListKey) {
        final String cardDataKey = boardKey + "+" + cardListKey + "+" + cardKey;
        final String cardDataDesKey = boardKey + "+" + desCardListKey + "+" + cardKey;

        arrCardRef(boardKey, cardListKey).child(cardKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Card card = dataSnapshot.getValue(Card.class);
                arrCardRef(boardKey, cardListKey).child(cardKey).removeValue();
                arrCardRef(boardKey, desCardListKey).child(cardKey).setValue(card);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        descriptionCardRef(cardDataKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String des = dataSnapshot.getValue(String.class);
                descriptionCardRef(cardDataKey).removeValue();
                descriptionCardRef(cardDataDesKey).setValue(des);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        arrCommentListRef(cardDataKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    Comment comment = item.getValue(Comment.class);
                    arrCommentListRef(cardDataKey).child(item.getKey()).removeValue();
                    arrCommentListRef(cardDataDesKey).child(item.getKey()).setValue(comment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        arrWorkListRef(cardDataKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> works = dataSnapshot.getChildren().iterator();
                while (works.hasNext()) {
                    DataSnapshot item = works.next();
                    if (item.getKey().equals("arrTask")) {
                        Iterator<DataSnapshot> workList = item.getChildren().iterator();
                        while (workList.hasNext()) {
                            DataSnapshot work = workList.next();
                            String workKey = work.getKey();
                            Iterator<DataSnapshot> taskList = work.getChildren().iterator();
                            while (taskList.hasNext()) {
                                DataSnapshot task = taskList.next();
                                String taskKey = task.getKey();
                                Task task1 = task.getValue(Task.class);
                                arrTaskListRef(cardDataKey, workKey).child(taskKey).removeValue();
                                arrTaskListRef(cardDataDesKey, workKey).child(taskKey).setValue(task1);
                                if (getmMvpView() != null) {
                                    getmMvpView().showMessge("Success");
                                }
                            }
                        }
                        return;
                    }
                    WorkList work = item.getValue(WorkList.class);
                    arrWorkListRef(cardDataKey).child(item.getKey()).removeValue();
                    arrWorkListRef(cardDataDesKey).child(item.getKey()).setValue(work);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
