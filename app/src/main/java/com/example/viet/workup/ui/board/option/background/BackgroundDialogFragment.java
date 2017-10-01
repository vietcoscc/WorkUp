package com.example.viet.workup.ui.board.option.background;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.STAR_BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.UID;

/**
 * Created by viet on 15/09/2017.
 */

public class BackgroundDialogFragment extends BaseDialogFragment implements BackgroundMvpView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Inject
    BackgroundPresenter<BackgroundMvpView> mPresenter;
    private String mBoardUid;
    private String mBoardKey;
    private boolean isStar;
    private BackgroundRecyclerViewAdapter mAdapter;

    public static BackgroundDialogFragment newInstance(String uid, String boardKey, boolean isStar) {
        Bundle args = new Bundle();
        args.putString(UID, uid);
        args.putString(BOARD, boardKey);
        args.putBoolean(STAR_BOARD, isStar);
        BackgroundDialogFragment fragment = new BackgroundDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardUid = getArguments().getString(UID);
            mBoardKey = getArguments().getString(BOARD);
            isStar = getArguments().getBoolean(STAR_BOARD);
        }
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_background_board, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new BackgroundRecyclerViewAdapter(1);
        recyclerView.setAdapter(mAdapter);
        mDialog.setView(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mDialog.setMessage("Background");
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
                        mPresenter.onUpdateBackground(isStar, mBoardUid, mBoardKey, mAdapter.getmCurrentPosition());
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
