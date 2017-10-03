package com.example.viet.workup.ui.board.card.move;

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
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;
import com.example.viet.workup.model.CardList;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD_LIST;

/**
 * Created by viet on 01/10/2017.
 */

public class CardMovingDialog extends BaseDialogFragment implements CardMovingMvpView {
    @Inject
    CardMovingPresenter<CardMovingMvpView> mPresenter;
    @BindView(R.id.spinner)
    Spinner spinner;
    private String mBoardKey;
    private String mCardListKey;
    private String mCardKey;
    private ArrayList<String> mArrCardList = new ArrayList<>();
    private ArrayList<String> mArrCardListKey = new ArrayList<>();
    private ArrayAdapter<String> mSpinnerAdapter;

    public static CardMovingDialog newInstance(String boardkey, String cardListKey, String cardKey) {

        Bundle args = new Bundle();
        args.putString(BOARD, boardkey);
        args.putString(CARD_LIST, cardListKey);
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
            mCardListKey = getArguments().getString(CARD_LIST);
            mCardKey = getArguments().getString(CARD);
        }
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        mPresenter.onReceiveCardList(mBoardKey, mCardListKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_card_moving, container, false);
        ButterKnife.bind(this, view);
        mSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mArrCardList);
        spinner.setAdapter(mSpinnerAdapter);
        mDialog.setView(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mDialog.setMessage("Move card to ");
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btn = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int) spinner.getSelectedItemId();
                        if (mCardListKey.equals(mArrCardListKey.get(position))) {
                            Toast.makeText(mContext, "same list", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mPresenter.onMoveCard(mBoardKey, mCardListKey, mCardKey, mArrCardListKey.get(position));
                        mDialog.dismiss();
                    }
                });
            }
        });
        return mDialog;
    }

    @Override
    public void showCardList(CardList cardList) {
        mArrCardList.add(cardList.getTitle());
        mArrCardListKey.add(cardList.getKey());
        mSpinnerAdapter.notifyDataSetChanged();
    }
}
