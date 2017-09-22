package com.example.viet.workup.ui.image.main;


import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.image.Category;

import java.util.ArrayList;

/**
 * Created by viet on 29/08/2017.
 */

public interface ImageMvpView extends MvpView {
    void displayCategory(ArrayList<Category> arCategory);
}
