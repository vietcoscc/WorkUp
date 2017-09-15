package com.example.viet.workup.ui.work;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 13/09/2017.
 */

public interface WorkMvpPresenter<V extends WorkMvpView> extends MvpPresenter<V> {
    void onReceiveLabel(String cardKey);

    void onReceiveMember(String cardKey);

    void onReceiveAttachment(String cardKey);

    void onReceiveWorkList(String cardKey);

    void onReceiveComment(String cardKey);

    void onReveiveDueDate(String cardKey);
}
