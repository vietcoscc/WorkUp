package com.example.viet.workup.ui.board.card.move;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 01/10/2017.
 */

public interface CardMovingMvpPresenter<V extends CardMovingMvpView> extends MvpPresenter<V> {
    void onReceiveCardList(String boardKey, String cardListKey);

    void onMoveCard(String boardKey, String cardListKey, String cardKey, String desCardListKey);
}
