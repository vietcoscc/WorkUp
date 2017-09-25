package com.example.viet.workup.ui.board.card;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 04/09/2017.
 */

public interface CardMvpPresenter<V extends CardMvpView> extends MvpPresenter<V> {
    void onReceiveTitle(String boardKey, String cardListKey);

    void onChangeTitle(String boardKey, String cardListKey, String title);

    void onReceiveData(String boardKey, String cardListKey);

    void onCardClick(String boardKey, String cardListKey, String position);
}
