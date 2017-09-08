package com.example.viet.workup.base;

/**
 * Created by viet on 01/09/2017.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V mMvpView;

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public V getmMvpView() {
        return mMvpView;
    }
}
