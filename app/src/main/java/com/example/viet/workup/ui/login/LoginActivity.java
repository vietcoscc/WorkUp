package com.example.viet.workup.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.manager.AccountManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginMvpView, View.OnClickListener {
    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "LoginActivity";

    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnLoginWithGoogle)
    SignInButton btnLoginWithGoogle;

    private AccountManager mAccountManager = AccountManager.getsInstance();
    private GoogleApiClient mGoogleApiClient;

    @Inject
    LoginPresenter<LoginMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        initViews();
        initGoogleLogin();
    }

    private void initGoogleLogin() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onConnectionFailed");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    private void initViews() {
        edtUserName.setText("vietcoscc2@gmail.com");
        edtPassword.setText("aendtbcds");
        btnCancel.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLoginWithGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                mPresenter.onButtonCancelClick();
                break;
            case R.id.btnLogin:
                mPresenter.onButtonLoginClick();
                break;
            case R.id.btnLoginWithGoogle:
                mPresenter.onButtonLoginWithGoogleClick();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                mAccountManager.firebaseAuthWithGoogle(acct, this);
            } else {
                Log.i(TAG, "Failed...");
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showLoginProgress() {
        mAccountManager.signOut();
        mAccountManager.signIn(edtUserName.getText().toString(), edtPassword.getText().toString(), this);
    }

    @Override
    public void showLoginWithGoogleProgress() {
        mAccountManager.signOut();
        signIn();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onDetach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }
}
