package com.example.viet.workup.ui.board.background;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 15/09/2017.
 */

public interface BackgroundMvpPresenter<V extends BackgroundMvpView> extends MvpPresenter<V> {
    void onUpdateBackground(boolean isStar, String uid, String boardKey, int currentPosition);
}
