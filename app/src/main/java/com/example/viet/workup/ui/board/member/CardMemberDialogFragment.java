package com.example.viet.workup.ui.board.member;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.UserInfo;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.STAR_BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.UID;

/**
 * Created by viet on 15/09/2017.
 */

public class CardMemberDialogFragment extends BaseDialogFragment implements CardMemberMvpView {
    public static final String TAG = "CardMemberDialogFragment";
    @BindView(R.id.recyclerViewMember)
    RecyclerView recyclerViewMember;

    private CardMemberRecyclerViewAdapetr mAdapter;
    @Inject
    CardMemberPresenter<CardMemberMvpView> mPresenter;
    private String mBoardKey;
    private String mBoardUid;
    private boolean isStar;
    private ArrayList<UserInfo> arrUserInfoCard = new ArrayList<>();
    private ArrayList<UserInfo> arrUserInfo = new ArrayList<>();
    private ArrayList<Boolean> arrBit = new ArrayList<>();

    public static CardMemberDialogFragment newInstance(String boardKey, String boardUid, boolean isStar) {
        Bundle args = new Bundle();
        args.putString(BOARD, boardKey);
        args.putString(UID, boardUid);
        args.putBoolean(STAR_BOARD, isStar);
        CardMemberDialogFragment fragment = new CardMemberDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardKey = getArguments().getString(BOARD);
            mBoardUid = getArguments().getString(UID);
            isStar = getArguments().getBoolean(STAR_BOARD);
        }
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
//        mPresenter.onReceiveMemeber(mBoardKey);
        mPresenter.onReceiveBoardMember(mBoardKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_card_member, container, false);
        ButterKnife.bind(this, view);
        recyclerViewMember.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        Log.e(TAG,mBoardUid);
        Log.e(TAG,AccountManager.getsInstance().getCurrentUser().getUid());
        mAdapter = new CardMemberRecyclerViewAdapetr(arrUserInfo, mBoardKey, isStar, mBoardUid.equals(AccountManager.getsInstance().getCurrentUser().getUid()));
        recyclerViewMember.setAdapter(mAdapter);
        mDialog.setView(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mDialog.setTitle("Add member");
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setVisibility(View.GONE);
                Button button2 = mDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                button2.setVisibility(View.GONE);
            }
        });
        return mDialog;
    }

    @Override
    public void showMember(final UserInfo userInfo) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) mContext;
        appCompatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrUserInfoCard.add(userInfo);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void showBoardMember(final UserInfo userInfo) {

        AppCompatActivity appCompatActivity = (AppCompatActivity) mContext;
        appCompatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrBit.add(inArrCard(userInfo));
                mAdapter.notifyDataSetChanged();
                mAdapter.addItem(userInfo);
            }
        });
    }

    private boolean inArrCard(UserInfo userInfo) {
        for (int i = 0; i < arrUserInfoCard.size(); i++) {
            if (arrUserInfoCard.get(i).getUid().equals(userInfo.getUid())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showFailure() {

    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
