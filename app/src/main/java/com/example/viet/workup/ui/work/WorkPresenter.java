package com.example.viet.workup.ui.work;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.model.WorkList;
import com.example.viet.workup.utils.CalendarUtils;
import com.example.viet.workup.utils.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCommentListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrWorkListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardDataRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.commentCountRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.coverImageCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.descriptionCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.dueDateCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.labelCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.memberCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.titleCardRed;

/**
 * Created by viet on 13/09/2017.
 */

public class WorkPresenter<V extends WorkMvpView> extends BasePresenter<V> implements WorkMvpPresenter<V> {
    public static final String TAG = "WorkPresenter";
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public WorkPresenter() {
    }

    @Override
    public void onDeleteLabel(String cardKey, String labelKey) {
        labelCardRef(cardKey).child(labelKey).removeValue();
    }

    @Override
    public void onDeleteCard(String cardKey, String title) {
        cardDataRef(cardKey).removeValue();
        cardRef(cardKey).removeValue();
        String from = mAccountManager.getCurrentUser().getDisplayName();
        String message = " removed card  ";
        String target = title;
        String timeStamp = CalendarUtils.getCurrentTime() + " " + CalendarUtils.getCurrentDate();
        arrActivityRef(cardKey.split("\\+")[1]).push().setValue(new BoardUserActivity(from, message, target, timeStamp, false));
        if (getmMvpView() != null) {
            getmMvpView().finishActivity();
        }
    }

    @Override
    public void onReceiveCoverImage(String cardKey) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        coverImageCardRef(cardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                if (getmMvpView() != null) {
                    getmMvpView().showCoverImage(url);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onReceiveTitle(String cardKey) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        titleCardRed(cardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.getValue(String.class);
                if (getmMvpView() == null) {
                    return;
                }
                if (TextUtils.isEmpty(title)) {
                    getmMvpView().finishActivity();
                }
                getmMvpView().showTitle(title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChangeTitle(String cardKey, String title) {
        if (DataUtils.isCardTitleValid(title)) {
            titleCardRed(cardKey).setValue(title.trim());
        }
    }

    @Override
    public void onReceiveDescription(String cardKey) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        descriptionCardRef(cardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String des = dataSnapshot.getValue(String.class);
                if (TextUtils.isEmpty(des)) {
                    return;
                }
                if (getmMvpView() != null) {
                    getmMvpView().showDes(des);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChangeDescription(String cardKey, String des) {
        if (DataUtils.isCardDescriptionValid(des)) {
            descriptionCardRef(cardKey).setValue(des);
        }

    }

    @Override
    public void onReceiveLabel(String cardKey) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        labelCardRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Label label = dataSnapshot.getValue(Label.class);
                        label.setKey(dataSnapshot.getKey());
                        if (label == null) {
                            return;
                        }
                        if (getmMvpView() != null) {
                            getmMvpView().showLabel(label);
                        }
                    }
                }.start();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (getmMvpView() != null) {
                    getmMvpView().deleteLabel(dataSnapshot.getKey());
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
    public void onReceiveMember(String cardKey) {
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
                        if (userInfo == null) {
                            return;
                        }
                        if (getmMvpView() != null) {
                            getmMvpView().showMemeber(userInfo);
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
    }

    @Override
    public void onReceiveWorkList(String cardKey) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        arrWorkListRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("arrTask")) {
                    return;
                }
                WorkList workList = dataSnapshot.getValue(WorkList.class);
                workList.setKey(dataSnapshot.getKey());
                if (workList == null) {
                    return;
                }
                if (getmMvpView() != null) {
                    getmMvpView().showWordList(workList);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("arrTask")) {
                    return;
                }
                WorkList workList = dataSnapshot.getValue(WorkList.class);
                workList.setKey(dataSnapshot.getKey());
                if (getmMvpView() != null) {
                    getmMvpView().changeWorkList(workList);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (getmMvpView() != null) {
                    getmMvpView().deleteWorkList(dataSnapshot.getKey());
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
    public void onReceiveComment(String cardKey) {
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        arrCommentListRef(cardKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                if (comment == null) {
                    return;
                }
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
        if (TextUtils.isEmpty(cardKey)) {
            Log.e(TAG, "Empty!");
            return;
        }
        dueDateCardRef(cardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        DueDate dueDate = dataSnapshot.getValue(DueDate.class);
                        if (dueDate == null) {
                            return;
                        }
                        if (dueDate != null) {
                            if (getmMvpView() != null) {
                                getmMvpView().showDueDate(dueDate);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onAddComment(final String cardKey, final String content) {
        if (TextUtils.isEmpty(cardKey) || TextUtils.isEmpty(content)) {
            Log.e(TAG, "Empty!");
            return;
        }
        getmMvpView().resetTextComment();
        arrCommentListRef(cardKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                Comment comment = new Comment(mAccountManager.getCurrentUser().getUid(), content.trim(), CalendarUtils.getCurrentTime());
                arrCommentListRef(cardKey).push().setValue(comment)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    commentCountRef(cardKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int count = dataSnapshot.getValue(Integer.class);
                                            commentCountRef(cardKey).setValue(count + 1);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
