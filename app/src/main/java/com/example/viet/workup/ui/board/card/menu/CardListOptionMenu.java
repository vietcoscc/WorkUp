package com.example.viet.workup.ui.board.card.menu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BasePopupMenu;
import com.example.viet.workup.ui.board.card.create_dialog.CardCreatingDialog;

import javax.inject.Inject;

/**
 * Created by viet on 12/09/2017.
 */

public class CardListOptionMenu extends BasePopupMenu implements CardListMvpView, MenuItem.OnMenuItemClickListener {
    private MenuItem mItemAddCard;
    private MenuItem mItemMoveList;
    private MenuItem mItemArchiveList;
    private String mBoardKey;
    private String mCardListKey;
    private Context mContext;
    @Inject
    CardListPresenter<CardListMvpView> mPresenter;

    public CardListOptionMenu(Context context, View anchor, String boardKey, String cardListKey) {
        super(context, anchor);
        this.mContext = context;
        this.mBoardKey = boardKey;
        this.mCardListKey = cardListKey;
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        initViews();
    }

    private void initViews() {
        getMenuInflater().inflate(R.menu.card_list_popup_menu, getMenu());
        mItemAddCard = getMenu().findItem(R.id.action_add);
        mItemMoveList = getMenu().findItem(R.id.action_move);
        mItemArchiveList = getMenu().findItem(R.id.action_archive_list);
        mItemAddCard.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add) {
            CardCreatingDialog cardCreatingDialog = CardCreatingDialog.newInstance(mBoardKey, mCardListKey);
            AppCompatActivity appCompatActivity = (AppCompatActivity) mContext;
            cardCreatingDialog.show(appCompatActivity.getSupportFragmentManager(), "");
        }
        return false;
    }
}
