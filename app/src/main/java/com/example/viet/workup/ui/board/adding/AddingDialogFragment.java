package com.example.viet.workup.ui.board.adding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;


public class AddingDialogFragment extends BaseDialogFragment implements AddingMvpView {
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @Inject
    AddingPresenter<AddingMvpView> mPresenter;
    private String mBoardKey;

    public AddingDialogFragment() {
        // Required empty public constructor
    }

    public static AddingDialogFragment newInstance(String boardKey) {
        AddingDialogFragment fragment = new AddingDialogFragment();
        Bundle args = new Bundle();
        args.putString(BOARD, boardKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardKey = getArguments().getString(BOARD);
        }
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Add list");
        builder.setPositiveButton("Create List", null);
        mDialog = builder.create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button button = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onButtonAddListClick(edtTitle.getText().toString(), mBoardKey);
                        button.setClickable(false);
                    }
                });
            }
        });
        return mDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_list_creating, container, false);
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


}
