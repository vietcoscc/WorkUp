package com.example.viet.workup.ui.main.board;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 07/09/2017.
 */

public class BoardCreatingDialog extends BaseDialogFragment implements BoardCreatingMvpView {
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.checkbox)
    CheckBox checkBox;
    private Context mContext;
    @Inject
    BoardCreatingPresenter<BoardCreatingMvpView> mPresenter;

    public static BoardCreatingDialog newInstance() {
        Bundle args = new Bundle();
        BoardCreatingDialog fragment = new BoardCreatingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        mContext = getContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        builder.setMessage("Create board");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Create", null);
        mDialog = builder.create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button button = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isOnProgress()){
                            return;
                        }
                        if (TextUtils.isEmpty(edtName.getText().toString())) {
                            Toast.makeText(mContext, "Board name must not empty !", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mPresenter.onCreateBoardButtonClick(edtName.getText().toString(), spinner.getSelectedItem().toString(), checkBox.isChecked());
                    }
                });
            }
        });
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_board_creating, null);
        ButterKnife.bind(this, view);
        mDialog.setView(view);
        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        initData();
        return view;
    }

    private void initData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, new String[]{"No group"});
        spinner.setAdapter(adapter);
    }

    @Override
    public void showMessge(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
