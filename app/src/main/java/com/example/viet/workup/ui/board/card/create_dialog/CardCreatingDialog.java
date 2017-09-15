package com.example.viet.workup.ui.board.card.create_dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD_LIST;

/**
 * Created by viet on 12/09/2017.
 */

public class CardCreatingDialog extends BaseDialogFragment implements CardCreatingMvpView {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @Inject
    CardCreatingPresenter<CardCreatingMvpView> mPresenter;
    private String mBoardKey;
    private String mCardListKey;

    @SuppressLint("ValidFragment")
    private CardCreatingDialog() {
    }

    public static CardCreatingDialog newInstance(String boardkey, String cardListKey) {
        Bundle args = new Bundle();
        args.putString(BOARD, boardkey);
        args.putString(CARD_LIST, cardListKey);
        CardCreatingDialog fragment = new CardCreatingDialog();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Create card", null);
        mDialog = builder.create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = tvTitle.getText().toString();
                        String description = tvDescription.getText().toString();
                        mPresenter.onCreateCardButtonClick(mBoardKey, mCardListKey, title, description);
                        button.setClickable(false);
                    }
                });
            }
        });
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_card_creating, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        mDialog.setView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void dismissCardCreatingDialog() {
        mDialog.dismiss();
    }

    @Override
    public void showCardCreatingError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
