package com.example.viet.workup.ui.board.card.menu.delete;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.viet.workup.base.BaseDialogFragment;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD_LIST;

/**
 * Created by viet on 18/09/2017.
 */

public class CardDeletingDialog extends BaseDialogFragment implements CardDeletingMvpView {
    @Inject
    CardDeletingPresenter<CardDeletingMvpView> mPresenter;
    private String mBoardKey;
    private String mCardListKey;

    public static CardDeletingDialog newInstance(String boardKey, String cardListKey) {

        Bundle args = new Bundle();
        args.putString(BOARD, boardKey);
        args.putString(CARD_LIST, cardListKey);
        CardDeletingDialog fragment = new CardDeletingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardKey = getArguments().getString(BOARD);
            mCardListKey = getArguments().getString(CARD_LIST);
        }
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mDialog.setMessage("Are you sure want to delete ?");
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isOnProgress()) {
                            return;
                        }
                        mPresenter.onDeleteCardList(mBoardKey, mCardListKey);
                    }
                });
            }
        });
        return mDialog;
    }
    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
