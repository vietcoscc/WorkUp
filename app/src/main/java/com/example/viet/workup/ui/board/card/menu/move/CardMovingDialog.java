package com.example.viet.workup.ui.board.card.menu.move;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;
import com.example.viet.workup.model.Board;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD;

/**
 * Created by viet on 17/09/2017.
 */

public class CardMovingDialog extends BaseDialogFragment implements CardMovingMvpView {
    private String mBoardKey;
    private String mCardKey;
    @BindView(R.id.spinner)
    Spinner spinner;

    private ArrayList<String> arrBoard = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<Board> arrBoardInfo = new ArrayList<>();

    @Inject
    CardMovingPresenter<CardMovingMvpView> mPresenter;

    public static CardMovingDialog newInstance(String boardKey, String cardKey) {

        Bundle args = new Bundle();
        args.putString(BOARD, boardKey);
        args.putString(CARD, cardKey);
        CardMovingDialog fragment = new CardMovingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardKey = getArguments().getString(BOARD);
            mCardKey = getArguments().getString(CARD);
        }
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        mPresenter.onReceiveBoardList(mBoardKey);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mDialog.setMessage("Move to ");
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isOnProgress()) {
                            return;
                        }
                        if(spinner.getSelectedItemPosition()>-1) {
                            String uid = arrBoardInfo.get(spinner.getSelectedItemPosition()).getKey();
                            mPresenter.onRemoveList(mBoardKey, mCardKey, uid);
                        }
                    }
                });
            }
        });

        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_card_moving, container, false);
        ButterKnife.bind(this, view);
        arrayAdapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, arrBoard);
        spinner.setAdapter(arrayAdapter);
        mDialog.setView(view);
        return view;
    }

    @Override
    public void showBoard(Board board) {
        arrBoard.add(board.getTitle() + ":" + board.getKey());
        arrBoardInfo.add(board);
        arrayAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
