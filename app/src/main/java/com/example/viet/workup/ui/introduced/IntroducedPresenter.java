package com.example.viet.workup.ui.introduced;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;

import javax.inject.Inject;

/**
 * Created by viet on 02/09/2017.
 */

public class IntroducedPresenter<V extends IntroducedMvpView> extends BasePresenter<V> implements IntroducedMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public IntroducedPresenter() {
    }

    @Override
    public void onCheckLogin() {

        if (mAccountManager.getmAuth() != null && mAccountManager.getmAuth().getCurrentUser() != null) {

            getmMvpView().showMainActivity();
            if (getmMvpView() == null) {
                return;
            }
            getmMvpView().showResultLogin("Loged in");

        } else {
            if (getmMvpView() == null) {
                return;
            }
            getmMvpView().showResultLogin("...");
        }
    }

    @Override
    public void onButtonLoginClick() {
        getmMvpView().showLoginActivity();
    }

    @Override
    public void onButtonRegisterClick() {
        getmMvpView().showRegisterActivity();
    }
}
