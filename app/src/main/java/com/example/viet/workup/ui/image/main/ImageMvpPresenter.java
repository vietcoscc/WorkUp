package com.example.viet.workup.ui.image.main;

import android.app.ProgressDialog;

import com.example.viet.workup.base.MvpPresenter;

/**
 * Created by viet on 29/08/2017.
 */

public interface ImageMvpPresenter<V extends ImageMvpView> extends MvpPresenter<V> {

    void getCategory();

}
