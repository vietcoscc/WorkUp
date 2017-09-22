package com.example.viet.workup.ui.board.member;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.UserInfo;

/**
 * Created by viet on 15/09/2017.
 */

public interface CardMemberMvpView extends MvpView {
    void showMember(UserInfo userInfo);
    void showBoardMember(UserInfo userInfo);
    void showSuccess();

    void showFailure();
}
