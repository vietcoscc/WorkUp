package com.example.viet.workup.ui.board.card.menu.delete;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 18/09/2017.
 */

public interface CardDeletingMvpPresenter<V extends CardDeletingMvpView> extends MvpPresenter<V> {

    void onDeleteCardList(String boardKey, String cardListKey, String cardListName);

}
