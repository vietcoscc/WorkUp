package com.example.viet.workup.ui.main.menu;

import com.example.viet.workup.base.MvpPresenter;
import com.example.viet.workup.model.Board;

import java.util.ArrayList;

/**
 * Created by viet on 08/09/2017.
 */

public interface BoardOptionMvpPresenter<V extends BoardOptionMvpView> extends MvpPresenter<V> {
    void onUnstarItemClick(Board board, ArrayList<Board> arrBoard, int position);

    void onStarItemClick(Board board, ArrayList<Board> arrBoard, int position);
}
