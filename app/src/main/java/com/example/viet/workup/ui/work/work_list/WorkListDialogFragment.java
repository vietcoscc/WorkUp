package com.example.viet.workup.ui.work.work_list;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;
import com.example.viet.workup.utils.DataUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD;

/**
 * Created by viet on 15/09/2017.
 */

public class WorkListDialogFragment extends BaseDialogFragment implements WorkListMvpView {
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @Inject
    WorkListPresenter<WorkListMvpView> mPresenter;
    private String mCardKey;

    public static WorkListDialogFragment newInstance(String cardKey) {
        Bundle args = new Bundle();
        args.putString(CARD, cardKey);
        WorkListDialogFragment fragment = new WorkListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCardKey = getArguments().getString(CARD);
        }
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_work_list_creating, container, false);
        ButterKnife.bind(this, view);
        edtTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        mDialog.setView(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mDialog.setTitle("Add work list");
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
                        if (DataUtils.isWorkListNameValid(edtTitle.getText().toString())) {
                            mPresenter.onAddWorkList(mCardKey, edtTitle.getText().toString());
                        } else {
                            Toast.makeText(mContext, "WorkList name invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return mDialog;
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
