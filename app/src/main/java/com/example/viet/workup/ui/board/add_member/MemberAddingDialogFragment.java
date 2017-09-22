package com.example.viet.workup.ui.board.add_member;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;
import com.example.viet.workup.model.UserInfo;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.STAR_BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.UID;

/**
 * Created by viet on 16/09/2017.
 */

public class MemberAddingDialogFragment extends BaseDialogFragment implements MemberAddingMvpView {
    @Inject
    MemberAddingPresenter<MemberAddingMvpView> mPresenter;

    @BindView(R.id.tvEmail)
    AutoCompleteTextView tvEmail;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> arrUser = new ArrayList<>();
    private ArrayList<UserInfo> arrUserInfo = new ArrayList<>();
    private String mBoardKey;
    private boolean isStar;
    private String mBoardUid;

    public static MemberAddingDialogFragment newInstance(String boardUid, String boardKey, boolean isStar) {

        Bundle args = new Bundle();
        args.putString(UID, boardUid);
        args.putString(BOARD, boardKey);
        args.putBoolean(STAR_BOARD, isStar);
        MemberAddingDialogFragment fragment = new MemberAddingDialogFragment();
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
        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, arrUser);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mDialog.setMessage("Add member");
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isOnProgress()) {
                    return;
                }
                mPresenter.onAddMember(mBoardUid, mBoardKey, isStar, arrUserInfo, tvEmail.getText().toString());
            }
        });
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_member_adding, container, false);
        ButterKnife.bind(this, view);
        mPresenter.onRecevedUser();
        tvEmail.setAdapter(mAdapter);
        tvEmail.setThreshold(1);
        tvEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        mDialog.setView(view);
        return view;
    }

    @Override
    public void showEmail(String email) {
        mAdapter.add(email);
    }

    public void addUser(UserInfo user) {
        arrUserInfo.add(user);
    }

    @Override
    public void hideProgressDataLoading() {
        mProgressBar.hide();
    }


    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
