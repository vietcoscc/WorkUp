package com.example.viet.workup.ui.introduced;

import android.content.Context;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 02/09/2017.
 */

public interface IntroducedMvpPresenter<V extends IntroducedMvpView> extends MvpPresenter<V> {
    void onCheckLogin( );

    void onButtonLoginClick();

    void onButtonRegisterClick();
}
