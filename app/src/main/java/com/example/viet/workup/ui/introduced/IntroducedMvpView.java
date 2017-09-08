package com.example.viet.workup.ui.introduced;

import com.example.viet.workup.base.MvpView;

/**
 * Created by viet on 02/09/2017.
 */

public interface IntroducedMvpView extends MvpView {
    void showResultLogin(String message);

    void showLoginActivity();

    void showRegisterActivity();
}
