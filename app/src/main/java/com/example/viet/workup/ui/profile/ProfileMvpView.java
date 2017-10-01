package com.example.viet.workup.ui.profile;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.UserInfo;

/**
 * Created by viet on 02/09/2017.
 */

public interface ProfileMvpView extends MvpView {
    void showUserInfo(UserInfo userInfo);
}
