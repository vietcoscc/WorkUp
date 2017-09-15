package com.example.viet.workup.ui.work;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.model.WorkList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCommentListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrWorkListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.dueDateCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.labelCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.memberCardRef;

/**
 * Created by viet on 13/09/2017.
 */

public class WorkPresenter<V extends WorkMvpView> extends BasePresenter<V> implements WorkMvpPresenter<V> {
    @Inject
    public WorkPresenter() {
    }

    @Override
    public void onReceiveLabel(String cardKey) {
        labelCardRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Label label = dataSnapshot.getValue(Label.class);
                if (getmMvpView() != null) {
                    getmMvpView().showLabel(label);
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
    public void onReceiveMember(String cardKey) {
        memberCardRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                if (getmMvpView() != null) {
                    getmMvpView().showMemeber(userInfo);
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
    public void onReceiveAttachment(String cardKey) {

    }

    @Override
    public void onReceiveWorkList(String cardKey) {
        arrWorkListRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                WorkList workList = dataSnapshot.getValue(WorkList.class);
                if (getmMvpView() != null) {
                    getmMvpView().showWordList(workList);
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
    public void onReceiveComment(String cardKey) {
        arrCommentListRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                if (getmMvpView() != null) {
                    getmMvpView().showComment(comment);
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
    public void onReveiveDueDate(String cardKey) {
        dueDateCardRef(cardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DueDate dueDate = dataSnapshot.getValue(DueDate.class);
                getmMvpView().showDueDate(dueDate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
