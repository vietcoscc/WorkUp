package com.example.viet.workup.ui.main;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.starBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.unstarBoardRef;

/**
 * Created by viet on 01/09/2017.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onFloatingActionButtonClick() {
        getmMvpView().showDialogBoard();
    }

    @Override
    public void onReceiveData() {
        starBoardRef(mAccountManager.getmAuth().getCurrentUser().getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Board board = dataSnapshot.getValue(Board.class);
                        board.setKey(dataSnapshot.getKey());
                        if (getmMvpView() != null) {
                            getmMvpView().showStarBoardReceived(board);
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
        unstarBoardRef(mAccountManager.getmAuth().getCurrentUser().getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Board board = dataSnapshot.getValue(Board.class);
                        board.setKey(dataSnapshot.getKey());
                        if (getmMvpView() != null) {
                            getmMvpView().showUnstarBoardReceived(board);
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
}
