package com.example.viet.workup.ui.main.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BasePopupMenu;
import com.example.viet.workup.model.Board;
import com.example.viet.workup.ui.main.MyboardRecyclerViewAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by viet on 07/09/2017.
 */

public class BoardOptionMenu extends BasePopupMenu implements BoardOptionMvpView, MenuItem.OnMenuItemClickListener {

    private MenuItem itemSignStar;
    private MenuItem itemUnSignStar;
    private Board mBoard;
    private ArrayList<Board> mArrBoard;
    private int mPosition;
    private RecyclerView mRecyclerView;
    @Inject
    BoardOptionPresenter<BoardOptionMvpView> mPresenter;

    public BoardOptionMenu(Context context, View anchor, RecyclerView recyclerView, ArrayList<Board> arrBoard, int position) {
        super(context, anchor);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        getMenuInflater().inflate(R.menu.board_popup_menu, getMenu());
        this.mBoard = arrBoard.get(position);
        this.mArrBoard = arrBoard;
        this.mPosition = position;
        this.mRecyclerView = recyclerView;
        initViews();

    }

    private void initViews() {
        itemSignStar = getMenu().findItem(R.id.action_star);
        itemUnSignStar = getMenu().findItem(R.id.action_unstar);
        itemUnSignStar.setOnMenuItemClickListener(this);
        itemSignStar.setOnMenuItemClickListener(this);
        if (mBoard.isStar()) {
            itemSignStar.setVisible(false);
        } else {
            itemUnSignStar.setVisible(false);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_star) {
            mPresenter.onStarItemClick(mBoard, mArrBoard, mPosition);
        } else if (menuItem.getItemId() == R.id.action_unstar) {
            mPresenter.onUnstarItemClick(mBoard, mArrBoard, mPosition);
        }
        return false;
    }


    @Override
    public void removeItem(int position) {
        MyboardRecyclerViewAdapter adapter = (MyboardRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.removeItem(position);
    }
}
