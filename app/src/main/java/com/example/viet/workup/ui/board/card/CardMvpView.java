package com.example.viet.workup.ui.board.card;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.Card;

/**
 * Created by viet on 04/09/2017.
 */

public interface CardMvpView extends MvpView {
    void showListTitle(String listTitle);

    void showCard(Card card);

    void removeCard(Card card);

    void changeCard(Card card);

    void showCreateError(String message);

    void showCardDetail(String cardKey);
}
