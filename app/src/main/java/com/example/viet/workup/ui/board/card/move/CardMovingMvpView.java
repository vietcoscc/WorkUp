package com.example.viet.workup.ui.board.card.move;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.CardList;

/**
 * Created by viet on 01/10/2017.
 */

public interface CardMovingMvpView extends MvpView {
    void showCardList(CardList cardList);
}
