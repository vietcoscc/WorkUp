package com.example.viet.workup.ui.board.card.create_dialog;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 12/09/2017.
 */

public interface CardCreatingMvpPresenter<V extends CardCreatingMvpView> extends MvpPresenter<V> {
     void onCreateCardButtonClick(String boardKey,String cardListKey,String title,String description);
}
