package com.example.viet.workup.ui.work;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 13/09/2017.
 */

public interface WorkMvpPresenter<V extends WorkMvpView> extends MvpPresenter<V> {
    void onReceiveCoverImage(String cardKey);

    void onReceiveTitle(String cardKey);

    void onReceiveDescription(String cardKey);

    void onReceiveLabel(String cardKey);

    void onReceiveMember(String cardKey);

    void onReceiveWorkList(String cardKey);

    void onReceiveComment(String cardKey);

    void onReveiveDueDate(String cardKey);

    void onAddComment(String cardKey, String content);
}
