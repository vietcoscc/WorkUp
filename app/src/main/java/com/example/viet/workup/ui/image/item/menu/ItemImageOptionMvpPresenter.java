package com.example.viet.workup.ui.image.item.menu;

import com.example.viet.workup.base.MvpPresenter;
import com.example.viet.workup.model.image.Image;

/**
 * Created by viet on 20/09/2017.
 */

public interface ItemImageOptionMvpPresenter<V extends ItemImageOptionMvpView> extends MvpPresenter<V> {
    void onOpenImage();

    void onSaveImage(Image image);

    void onSetImage(Image image);
}
