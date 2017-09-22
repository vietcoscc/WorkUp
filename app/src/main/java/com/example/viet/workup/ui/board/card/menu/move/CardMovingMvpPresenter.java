package com.example.viet.workup.ui.board.card.menu.move;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 17/09/2017.
 */

public interface CardMovingMvpPresenter<V extends CardMovingMvpView> extends MvpPresenter<V> {
    void onReceiveBoardList(String thisBoard);

    void onRemoveList(String srcBoardKey, String cardListKey, String desBoardKey);
}
