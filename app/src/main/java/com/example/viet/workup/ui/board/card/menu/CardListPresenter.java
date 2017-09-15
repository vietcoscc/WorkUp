package com.example.viet.workup.ui.board.card.menu;

import com.example.viet.workup.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by viet on 12/09/2017.
 */

public class CardListPresenter<V extends CardListMvpView> extends BasePresenter<V> implements CardListMvpPresenter<V> {
    @Inject
    public CardListPresenter() {
    }
}
