package com.example.viet.workup.ui.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements RegisterMvpView, View.OnClickListener {
    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        btnCancel.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        edtUserName.setText("vietcoscc2@gmail.com");
        edtPassword.setText("aendtbcds");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnRegister:
                mAccountManager.signUp(edtUserName.getText().toString(),  edtPassword.getText().toString(), this);
                break;
        }
    }


}
