package com.example.viet.workup.ui.introduced;

import android.content.Context;
import android.content.Intent;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.ui.main.MainActivity;

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
    public void onCheckLogin(Context context) {
        if (mAccountManager.getmAuth() != null && mAccountManager.getmAuth().getCurrentUser() != null) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            getmMvpView().showResultLogin("Loged in");
        } else {
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
