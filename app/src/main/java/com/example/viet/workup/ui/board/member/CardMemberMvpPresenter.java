package com.example.viet.workup.ui.board.member;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 15/09/2017.
 */

public interface CardMemberMvpPresenter<V extends CardMemberMvpView> extends MvpPresenter<V> {
    void onReceiveMemeber(String cardKey);

    void onReceiveBoardMember(String boardKey);
}
