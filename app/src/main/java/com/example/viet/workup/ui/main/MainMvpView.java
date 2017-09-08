package com.example.viet.workup.ui.main;

import android.view.View;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.Board;

/**
 * Created by viet on 01/09/2017.
 */

public interface MainMvpView extends MvpView {
    void showDialogBoard();

    void showStarBoardReceived(Board board);

    void showUnstarBoardReceived(Board board);

    void showBoardOptionMenu(View view, int position);
}
