package com.example.viet.workup.ui.image.item;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.example.viet.workup.base.MvpPresenter;
import com.example.viet.workup.model.image.Image;

import java.util.ArrayList;


/**
 * Created by viet on 30/08/2017.
 */

public interface ItemMvpPresenter<V extends ItemMvpView> extends MvpPresenter<V> {
    void getLastest();

    void getCategoryItem( String catId);

    void createImageViewer(Context context, View view, int position, ArrayList<Image> arrImage);
}
