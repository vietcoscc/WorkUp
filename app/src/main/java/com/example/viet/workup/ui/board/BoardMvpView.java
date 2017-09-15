package com.example.viet.workup.ui.board;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.CardList;

/**
 * Created by viet on 02/09/2017.
 */

public interface BoardMvpView extends MvpView {
    void showArrCardList(CardList cardList);

    void showAddingListDialog();

    void showSuccess();

    void showFailure();
}
