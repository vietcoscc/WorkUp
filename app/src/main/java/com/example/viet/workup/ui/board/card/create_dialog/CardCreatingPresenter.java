package com.example.viet.workup.ui.board.card.create_dialog;

import android.support.annotation.NonNull;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.CardDetail;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.Task;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.model.WorkList;
import com.example.viet.workup.utils.CalendarUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrCardRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.cardDataRef;

/**
 * Created by viet on 12/09/2017.
 */

public class CardCreatingPresenter<V extends CardCreatingMvpView> extends BasePresenter<V> implements CardCreatingMvpPresenter<V> {
    @Inject
    public CardCreatingPresenter() {
    }

    @Override
    public void onCreateCardButtonClick(final String boardKey, final String cardListKey, final String title, final String description) {
        getmMvpView().showProgress();
        Label label = new Label("#e3952a", "NQV");
        DueDate dueDate = new DueDate(CalendarUtils.getDay(), CalendarUtils.getMonth(), CalendarUtils.getYear());
        UserInfo userInfo = new UserInfo("NQV", "", "", "https://lh4.googleusercontent.com/-PI_P73ggPbo/AAAAAAAAAAI/AAAAAAAAEoE/xx2k1aDSHlI/s96-c/photo.jpg", "");
        ArrayList<Label> arrLabel = new ArrayList<>();
        arrLabel.add(label);
        ArrayList<UserInfo> arrUserInfo = new ArrayList<>();
        arrUserInfo.add(userInfo);
        final Card card = new Card("", arrLabel, title, 1, 1, "1/6", dueDate, arrUserInfo, !description.isEmpty());
        arrCardRef(boardKey, cardListKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final long count = dataSnapshot.getChildrenCount();

                arrCardRef(boardKey, cardListKey).child(count + "")
                        .setValue(card)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                CardDetail cardDetail = generateCardDetail(card, description);
                                cardDataRef(boardKey + "+" + cardListKey + "+" + count).setValue(cardDetail)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                if (getmMvpView() != null) {
                                                    getmMvpView().hideProgress();
                                                    getmMvpView().dismissCardCreatingDialog();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                if (getmMvpView() != null) {
                                                    getmMvpView().hideProgress();
                                                    getmMvpView().dismissCardCreatingDialog();
                                                }
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (getmMvpView() != null) {
                                    getmMvpView().hideProgress();
                                    getmMvpView().dismissCardCreatingDialog();
                                }
                            }
                        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private CardDetail generateCardDetail(Card card, String description) {
        String dueTime = "14:09";
        Task task = new Task("Learning machine", false);
        ArrayList<Task> arrTask = new ArrayList<>();
        arrTask.add(task);
        arrTask.add(task);
        WorkList workList = new WorkList("title", arrTask);
        ArrayList<WorkList> arrWorkList = new ArrayList<>();
        arrWorkList.add(workList);
        arrWorkList.add(workList);
        arrWorkList.add(workList);
        arrWorkList.add(workList);

        UserInfo userInfo =
                new UserInfo("NQV", "", "",
                        "https://lh4.googleusercontent.com/-PI_P73ggPbo/AAAAAAAAAAI/AAAAAAAAEoE/xx2k1aDSHlI/s96-c/photo.jpg",
                        "");
        Comment comment = new Comment(userInfo, "Nguyen Quoc Viet", "");
        ArrayList<Comment> arrComment = new ArrayList<>();
        arrComment.add(comment);
        arrComment.add(comment);
        arrComment.add(comment);
        ArrayList<String> arrAttachFile = new ArrayList<>();
        arrAttachFile.add("https://lh4.googleusercontent.com/-PI_P73ggPbo/AAAAAAAAAAI/AAAAAAAAEoE/xx2k1aDSHlI/s96-c/photo.jpg");
        return new CardDetail(description, dueTime, arrAttachFile, arrWorkList, arrComment);
    }
}
