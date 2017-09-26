package com.example.viet.workup.ui.board;

import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.utils.ApplicationUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.boardImageUrlRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.memberBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.otherBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.starBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.unstarBoardRef;

/**
 * Created by viet on 02/09/2017.
 */

public class BoardPresenter<V extends BoardMvpView> extends BasePresenter<V> implements BoardMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();
    private static final String TAG = "BoardPresenter";

    @Inject
    public BoardPresenter() {
    }

    @Override
    public void onReceiveTitle(String uid, String boardKey, boolean isStar) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.getValue(String.class);
                if (TextUtils.isEmpty(title)) {
                    if (getmMvpView() != null) {
                        getmMvpView().finishAcitivity();
                    }
                }
                if (getmMvpView() != null) {
                    getmMvpView().showTitle(title);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if (isStar) {
            starBoardRef(uid).child(boardKey).child("title").addValueEventListener(valueEventListener);
        } else {
            unstarBoardRef(uid).child(boardKey).child("title").addValueEventListener(valueEventListener);
        }
    }

    @Override
    public void onReceiveData(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (getmMvpView() != null) {
            getmMvpView().showProgress();
        }
        boardDataRef(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
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
                    getmMvpView().showArrCardList(cardList);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, dataSnapshot.getKey());
                String cardListKey = dataSnapshot.getKey();
                if (getmMvpView() != null) {
                    getmMvpView().hideCardList(cardListKey);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        boardDataRef(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    ChildEventListener activityChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            BoardUserActivity boardUserActivity = dataSnapshot.getValue(BoardUserActivity.class);
            if (getmMvpView() != null) {
                getmMvpView().showArrActivity(boardUserActivity);
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
    };

    @Override
    public void onReceiveActivity(String key) {
        arrActivityRef(key).limitToLast(30).addChildEventListener(activityChildEventListener);
    }

    @Override
    public void removeActivityEvent(String key) {
        arrActivityRef(key).limitToLast(30).removeEventListener(activityChildEventListener);
    }

    @Override
    public void onReceiveMember(String key) {
        memberBoardRef(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey().equals(mAccountManager.getCurrentUser().getUid())) {
                    if (getmMvpView() != null) {
                        getmMvpView().finishAcitivity();
                    }
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
    public void onReceiveBackground(String uid, String boardKey, boolean isStar) {
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(boardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        boardImageUrlRef(isStar, uid, boardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    return;
                }
                Integer id = dataSnapshot.getValue(Integer.class);
                Log.i(TAG, id + "");
                if (getmMvpView() != null) {
                    getmMvpView().showBackground(ApplicationUtils.rawId(id));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDeleteBoard(final String uid, final String boardKey, final boolean isStar) {
        boardDataRef(boardKey).addChildEventListener(new ChildEventListener() {
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
                final String listKey = dataSnapshot.getKey();
                arrCardRef(boardKey, listKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String keyCard = dataSnapshot.getKey();
                        cardDataRef(boardKey + "+" + listKey + "+" + keyCard).removeValue();
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
                arrCardRef(boardKey, listKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrCardRef(boardKey, listKey).removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        boardDataRef(boardKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                memberBoardRef(boardKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        otherBoardRef(dataSnapshot.getKey()).child(uid + "+" + boardKey).removeValue();
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
                memberBoardRef(boardKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boardDataRef(boardKey).removeValue();

                        if (isStar) {
                            starBoardRef(uid).child(boardKey).removeValue();
                        } else {
                            unstarBoardRef(uid).child(boardKey).removeValue();
                        }
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
}
