package com.example.viet.workup.ui.main;

import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.example.viet.workup.model.CardList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.starBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.unstarBoardRef;

/**
 * Created by viet on 01/09/2017.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    private static final String TAG = "MainPresenter";
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onFloatingActionButtonClick() {
        getmMvpView().showDialogBoard();
    }

    @Override
    public void onOptionMenuActionSettingsClick() {

    }

    @Override
    public void onOptionMenuActionLogoutClick() {
        mAccountManager.signOut();
        getmMvpView().showIntroduce();
    }

    @Override
    public void onReceiveData() {
        starBoardRef(mAccountManager.getmAuth().getCurrentUser().getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Board board = onChilAdded(dataSnapshot);
                        if (getmMvpView() != null) {
                            getmMvpView().showStarBoardReceived(board);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Log.i(TAG, s);
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
                        Board board = onChilAdded(dataSnapshot);
                        if (getmMvpView() != null) {
                            getmMvpView().showUnstarBoardReceived(board);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Log.i(TAG, s);
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

    private Board onChilAdded(DataSnapshot dataSnapshot) {
        final String key = dataSnapshot.getKey();
        boardDataRef(key).addValueEventListener(getValueEventListener(key));
        Board board = dataSnapshot.getValue(Board.class);
        board.setKey(key);
        return board;
    }

    private ValueEventListener getValueEventListener(final String key) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    return;
                }
                CardList todo = new CardList("Todo", null);
                CardList doing = new CardList("Doing", null);
                CardList done = new CardList("Done", null);
                boardDataRef(key).push().setValue(todo);
                boardDataRef(key).push().setValue(doing);
                boardDataRef(key).push().setValue(done);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage() + " " + databaseError.getDetails());
            }
        };
    }
}
