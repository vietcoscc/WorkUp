package com.example.viet.workup.ui.board.add_member;

import com.example.viet.workup.base.MvpPresenter;
import com.example.viet.workup.model.UserInfo;

import java.util.ArrayList;

/**
 * Created by viet on 16/09/2017.
 */

public interface MemberAddingMvpPresenter<V extends MemberAddingMvpView> extends MvpPresenter<V> {
    void onRecevedUser();

    void onAddMember(String boardUid, String boardKey, final boolean isStar, ArrayList<UserInfo> arrUserInfo, String email);


}
