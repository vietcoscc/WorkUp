package com.example.viet.workup.ui.board;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 02/09/2017.
 */

public interface BoardMvpPresenter<V extends BoardMvpView> extends MvpPresenter<V> {
    void onReceiveData(String key);

    void onReceiveBackground(String uid, String boardKey, boolean isStar);
}
