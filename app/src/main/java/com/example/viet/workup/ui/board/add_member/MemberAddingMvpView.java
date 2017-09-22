package com.example.viet.workup.ui.board.add_member;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.UserInfo;

/**
 * Created by viet on 16/09/2017.
 */

public interface MemberAddingMvpView extends MvpView {
    void showEmail(String email);

    void addUser(UserInfo user);

    void hideProgressDataLoading();
}
