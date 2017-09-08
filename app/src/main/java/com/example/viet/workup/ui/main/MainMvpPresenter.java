package com.example.viet.workup.ui.main;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 01/09/2017.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void onFloatingActionButtonClick();

    void onReceiveData();
}
