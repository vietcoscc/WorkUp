package com.example.viet.workup.ui.work.label;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 15/09/2017.
 */

public interface LabelMvpPresenter<V extends LabelMvpView> extends MvpPresenter<V> {
    void onAddLabel(String cardKey, String color, String label);
}
