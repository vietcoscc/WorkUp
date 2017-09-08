package com.example.viet.workup.ui.login;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;

import javax.inject.Inject;

/**
 * Created by viet on 02/09/2017.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void onButtonCancelClick() {
        getmMvpView().finishActivity();
    }

    @Override
    public void onButtonLoginClick() {
        getmMvpView().showLoginProgress();
    }


    @Override
    public void onButtonLoginWithGoogleClick() {
        getmMvpView().showLoginWithGoogleProgress();
    }
}
