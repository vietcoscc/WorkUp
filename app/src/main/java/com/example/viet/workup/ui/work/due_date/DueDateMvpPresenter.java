package com.example.viet.workup.ui.work.due_date;

import com.example.viet.workup.base.MvpPresenter;
import com.example.viet.workup.model.DueDate;

/**
 * Created by viet on 15/09/2017.
 */

public interface DueDateMvpPresenter<V extends DueDateMvpView> extends MvpPresenter<V> {
    void onButtonDoneClick(String mKey, DueDate dueDate);
}
