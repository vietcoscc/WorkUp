package com.example.viet.workup.ui.work.label;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseDialogFragment;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.flask.colorpicker.ColorPickerView;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD;

/**
 * Created by viet on 15/09/2017.
 */

public class LabelDialogFragment extends BaseDialogFragment implements LabelMvpView {
    @BindView(R.id.color_picker_view)
    ColorPickerView colorPickerView;
    @BindView(R.id.edtLabel)
    EditText edtLabel;
    @Inject
    LabelMvpPresenter<LabelMvpView> mPresenter;
    private String mCardKey;
    private DatabaseReference labelCardRef;

    public LabelDialogFragment() {
    }

    public static LabelDialogFragment newInstance(String cardKey) {
        Bundle args = new Bundle();
        args.putString(CARD, cardKey);
        LabelDialogFragment labelDialogFragment = new LabelDialogFragment();
        labelDialogFragment.setArguments(args);
        return labelDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCardKey = getArguments().getString(CARD);
            FireBaseDatabaseUtils.labelCardRef(mCardKey);
        }

        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
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
                        String color = "#" + Integer.toHexString(colorPickerView.getSelectedColor());
                        Toast.makeText(mContext, color, Toast.LENGTH_SHORT).show();
                        mPresenter.onAddLabel(mCardKey, color, edtLabel.getText().toString());
                    }
                });
            }
        });
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_label_creating, container, false);

        ButterKnife.bind(this, view);

        mDialog.setView(view);
        return view;
    }

    @Override
    public void showSuccess() {
        Toast.makeText(mContext, "Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailure() {
        Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
