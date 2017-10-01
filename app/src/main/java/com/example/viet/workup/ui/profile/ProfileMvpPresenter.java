package com.example.viet.workup.ui.profile;

import android.net.Uri;

import com.example.viet.workup.base.MvpPresenter;

import java.io.InputStream;

/**
 * Created by viet on 02/09/2017.
 */

public interface ProfileMvpPresenter<V extends ProfileMvpView> extends MvpPresenter<V> {
    void onReceiveUserInfo();

    void onChangeDisplayName(String displayName);

    void onChangeAvatar(InputStream stream);
}
