package com.example.viet.workup.ui.work;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.model.WorkList;

/**
 * Created by viet on 13/09/2017.
 */

public interface WorkMvpView extends MvpView {
    void showCoverImage(String url);

    void showLabel(Label label);

    void showMemeber(UserInfo userInfo);

    void showWordList(WorkList workList);

    //
    void deleteWorkList(String key);

    void changeWorkList(WorkList workList);

    void showAttachment(String attachFile);

    //
    void showComment(Comment comment);

    void showDueDate(DueDate dueDate);

    void showTitle(String title);

    void showDes(String des);

    void resetTextComment();

    void finishActivity();

}
