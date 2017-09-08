package com.example.viet.workup.ui.main.menu;

import android.support.annotation.NonNull;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.starBoardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.unstarBoardRef;

/**
 * Created by viet on 08/09/2017.
 */

public class BoardOptionPresenter<V extends BoardOptionMvpView> extends BasePresenter<V> implements BoardOptionMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public BoardOptionPresenter() {
    }

    @Override
    public void onUnstarItemClick(final Board board, final ArrayList<Board> arrBoard, final int position) {
        getmMvpView().showProgress();
        unstarBoardRef(mAccountManager.getCurrentUser().getUid()).child(board.getKey())
                .setValue(board)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        starBoardRef(mAccountManager.getCurrentUser().getUid())
                                .child(board.getKey())
                                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (getmMvpView() != null) {
                                    arrBoard.remove(position);
                                    getmMvpView().dislayDataNotified();
                                    getmMvpView().hideProgress();
                                    getmMvpView().showMessge("onSuccess");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (getmMvpView() != null) {
                                    unstarBoardRef(mAccountManager.getCurrentUser().getUid()).child(board.getKey()).removeValue();
                                    getmMvpView().hideProgress();
                                    getmMvpView().showMessge("onFailure");
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                    getmMvpView().showMessge("onFailure");
                }
            }
        });
    }

    @Override
    public void onStarItemClick(final Board board, final ArrayList<Board> arrBoard, final int position) {
        getmMvpView().showProgress();
        starBoardRef(mAccountManager.getCurrentUser().getUid()).child(board.getKey())
                .setValue(board)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        unstarBoardRef(mAccountManager.getCurrentUser().getUid())
                                .child(board.getKey())
                                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (getmMvpView() != null) {
                                    getmMvpView().dislayDataNotified();
                                    arrBoard.remove(position);
                                    getmMvpView().hideProgress();
                                    getmMvpView().showMessge("onSuccess");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (getmMvpView() != null) {
                                    getmMvpView().hideProgress();
                                    starBoardRef(mAccountManager.getCurrentUser().getUid()).child(board.getKey()).removeValue();
                                    getmMvpView().showMessge("onFailure");
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                    getmMvpView().showMessge("onFailure");
                }
            }
        });
    }
}
