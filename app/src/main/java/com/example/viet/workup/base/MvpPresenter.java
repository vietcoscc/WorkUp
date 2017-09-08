package com.example.viet.workup.base;

/**
 * Created by viet on 01/09/2017.
 */

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);
    void onDetach();
}
