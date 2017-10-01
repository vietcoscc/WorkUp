package com.example.viet.workup.ui.board.option.add_member;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.model.image.OtherBoard;
import com.example.viet.workup.utils.CalendarUtils;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.memberBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.otherBoardRef;

/**
 * Created by viet on 16/09/2017.
 */

public class MemberAddingPresenter<V extends MemberAddingMvpView> extends BasePresenter<V> implements MemberAddingMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public MemberAddingPresenter() {
    }

    @Override
    public void onRecevedUser() {
        getmMvpView().showProgress();
        FireBaseDatabaseUtils.accountRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.exists()){
                    return;
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (getmMvpView() == null) {
                            return;
                        }
                        UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                        getmMvpView().showEmail(userInfo.getEmail());
                        getmMvpView().addUser(userInfo);
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
        FireBaseDatabaseUtils.accountRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getmMvpView().hideProgressDataLoading();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onAddMember(final String boardUid,
                            final String boardKey,
                            final boolean isStar,
                            ArrayList<UserInfo> arrUserInfo,
                            String email) {
        if (TextUtils.isEmpty(boardUid) || TextUtils.isEmpty(boardKey)
                || TextUtils.isEmpty(email)
                || arrUserInfo.isEmpty()) {
            return;
        }
        if (getmMvpView() != null) {
            getmMvpView().showProgress();
        }
        getUserInfoObservable(arrUserInfo, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserInfo>() {
            @Override
            public void accept(final UserInfo userInfo) throws Exception {
                if (userInfo == null) {
                    return;
                }
                if (userInfo.getUid().equals(mAccountManager.getCurrentUser().getUid())) {
                    getmMvpView().showMessge("Cant add yourself");
                    return;
                }

                if (userInfo.getUid().isEmpty()) {
                    getmMvpView().showMessge("No user");
                    return;
                }
                if (userInfo.getUid().equals(boardUid)) {
                    getmMvpView().showMessge("Cant add board initiator ");
                    return;
                }
                memberBoardRef(boardKey).child(userInfo.getUid()).setValue(userInfo);
                OtherBoard otherBoard = new OtherBoard(mAccountManager.getUserInfo().getUid(), boardKey, isStar);
                otherBoardRef(userInfo.getUid())
                        .child(mAccountManager.getUserInfo().getUid() + "+" + boardKey)
                        .setValue(otherBoard)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                String from = mAccountManager.getCurrentUser().getDisplayName();
                                String message = " added member : ";
                                String target = userInfo.getDisplayName();
                                String timeStamp = CalendarUtils.getCurrentTime() + " " + CalendarUtils.getCurrentDate();
                                arrActivityRef(boardKey).push().setValue(new BoardUserActivity(from, message, target, timeStamp,false));
                                if (getmMvpView() != null) {
                                    getmMvpView().hideProgress();
                                    getmMvpView().showMessge("onSuccess");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (getmMvpView() != null) {
                                    getmMvpView().hideProgress();
                                    getmMvpView().showMessge("onFailure");
                                }
                            }
                        });
            }
        });
    }


    private Observable<UserInfo> getUserInfoObservable(final ArrayList<UserInfo> arrUserInfo, final String email) {
        return Observable.fromCallable(new Callable<UserInfo>() {
            @Override
            public UserInfo call() throws Exception {
                for (int i = 0; i < arrUserInfo.size(); i++) {
                    if (email.equals(arrUserInfo.get(i).getEmail())) {
                        return arrUserInfo.get(i);
                    }
                }
                return new UserInfo("", "", "", "", "");
            }
        });
    }
}
