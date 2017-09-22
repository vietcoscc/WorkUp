package com.example.viet.workup.ui.board.list_card;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 04/09/2017.
 */

public interface ListAddingMvpPresenter<V extends ListAddingMvpView> extends MvpPresenter<V> {
    void onButtonAddListClick(String title,String boardKey);

}
