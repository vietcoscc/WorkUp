package com.example.viet.workup.ui.board.card.menu.create;

import com.example.viet.workup.base.MvpView;

/**
 * Created by viet on 12/09/2017.
 */

public interface CardCreatingMvpView extends MvpView {
    void dismissCardCreatingDialog();

    void showCardCreatingError(String message);
}
