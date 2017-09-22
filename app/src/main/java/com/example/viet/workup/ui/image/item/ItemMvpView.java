package com.example.viet.workup.ui.image.item;

import android.widget.PopupMenu;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.image.Image;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;


/**
 * Created by viet on 30/08/2017.
 */

public interface ItemMvpView extends MvpView {
    void displayImage(ArrayList<Image> arrImage);

    void displayImageViewer(ImageViewer imageViewer);

    void displayPopupMenu(PopupMenu popupMenu);
}
