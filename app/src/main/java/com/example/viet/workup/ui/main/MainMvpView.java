package com.example.viet.workup.ui.main;

import android.view.View;

import com.example.viet.workup.base.MvpView;
import com.example.viet.workup.model.Board;

/**
 * Created by viet on 01/09/2017.
 */

public interface MainMvpView extends MvpView {
    void showDialogBoard();

    void showIntroduce();

    void showStarBoardReceived(Board board);

    void showUnstarBoardReceived(Board board);

    void showOtherBoardReceived(Board board);

    void deleteStarBoardReceived(String board);

    void deleteUnstarBoardReceived(String board);

    void deleteOtherBoardReceived(String board);

    void removeOtherBoard(String boardUid, String otherBoardKey);

    void showUnstarBoardOptionMenu(View view, int position);

    void showStarBoardOptionMene(View view, int position);
}
