package com.example.viet.workup.ui.work.work_list;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 15/09/2017.
 */

public interface WorkListMvpPresenter<V extends WorkListMvpView> extends MvpPresenter<V> {
    void onAddWorkList(String cardKey,String title);
}
