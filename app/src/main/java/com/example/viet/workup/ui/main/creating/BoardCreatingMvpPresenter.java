package com.example.viet.workup.ui.main.creating;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 07/09/2017.
 */

public interface BoardCreatingMvpPresenter<V extends BoardCreatingMvpView> extends MvpPresenter<V> {

    void onCreateBoardButtonClick(String name, String group, boolean isChecked);

}
