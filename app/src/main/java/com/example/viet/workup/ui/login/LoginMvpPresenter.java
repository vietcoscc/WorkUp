package com.example.viet.workup.ui.login;

import android.content.Context;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 02/09/2017.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
    void onButtonCancelClick();

    void onButtonLoginClick();

    void onButtonLoginWithGoogleClick();

}
