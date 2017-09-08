package com.example.viet.workup.ui.login;

import com.example.viet.workup.base.MvpView;

/**
 * Created by viet on 02/09/2017.
 */

public interface LoginMvpView extends MvpView {
    void finishActivity();

    void showLoginProgress();

    void showLoginWithGoogleProgress();
}
