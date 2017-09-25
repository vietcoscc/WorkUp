package com.example.viet.workup.ui.board.card.menu.create;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.utils.CalendarUtils;
import com.example.viet.workup.utils.DataUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrActivityRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCardRef;

/**
 * Created by viet on 12/09/2017.
 */

public class CardCreatingPresenter<V extends CardCreatingMvpView> extends BasePresenter<V> implements CardCreatingMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public CardCreatingPresenter() {
    }

    @Override
    public void onCreateCardButtonClick(final String boardKey, final String cardListKey, final String title, final String description) {
        if (TextUtils.isEmpty(boardKey) || TextUtils.isEmpty(cardListKey)) {
            return;
        }
        if (!DataUtils.isCardTitleValid(title)) {
            if (getmMvpView() != null) {
                getmMvpView().showMessge("Card title invalid");
            }
            return;
        }
        if (!DataUtils.isCardDescriptionValid(description)) {
            if (getmMvpView() != null) {
                getmMvpView().showMessge("Card description invalid");
            }
            return;
        }
        getmMvpView().showProgress();
        Label label = new Label("#e3952a", mAccountManager.getUserInfo().getDisplayName());
        DueDate dueDate = new DueDate(CalendarUtils.getDay(), CalendarUtils.getMonth(), CalendarUtils.getYear());
        UserInfo userInfo = mAccountManager.getUserInfo();
        ArrayList<Label> arrLabel = new ArrayList<>();
        arrLabel.add(label);
        ArrayList<UserInfo> arrUserInfo = new ArrayList<>();
        arrUserInfo.add(userInfo);
        final Card card = new Card("", title, 0, "", dueDate, arrLabel,arrUserInfo, !description.isEmpty(), description);
        arrCardRef(boardKey, cardListKey).push().setValue(card).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                String from = mAccountManager.getCurrentUser().getDisplayName();
                String message = " create card  ";
                String target = card.getTitle();
                String timeStamp = CalendarUtils.getCurrentTime() + " " + CalendarUtils.getCurrentDate();
                arrActivityRef(boardKey).push().setValue(new BoardUserActivity(from, message, target, timeStamp));
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                    getmMvpView().dismissCardCreatingDialog();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().hideProgress();
                    getmMvpView().dismissCardCreatingDialog();
                }
            }
        });
    }

    //                CardDetail cardDetail = generateCardDetail(card, description);
//                cardDataRef(boardKey + "+" + cardListKey + "+" + count).setValue(cardDetail)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                if (getmMvpView() != null) {
//                                    getmMvpView().hideProgress();
//                                    getmMvpView().dismissCardCreatingDialog();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                if (getmMvpView() != null) {
//                                    getmMvpView().hideProgress();
//                                    getmMvpView().dismissCardCreatingDialog();
//                                }
//                            }
//                        });

}
