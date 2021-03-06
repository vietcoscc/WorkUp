package com.example.viet.workup.ui.introduced;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.ui.login.LoginActivity;
import com.example.viet.workup.ui.main.MainActivity;
import com.example.viet.workup.ui.register.RegisterActivity;
import com.viewpagerindicator.CirclePageIndicator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroducedActivity extends BaseActivity implements IntroducedMvpView, View.OnClickListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.circlePageIndicator)
    CirclePageIndicator circlePageIndicator;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Inject
    IntroducedPresenter<IntroducedMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduced);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        ButterKnife.bind(this);
        initViews();
        mPresenter.onCheckLogin();
    }

    private void initViews() {
        IntroducedViewPagerAdapter viewPagerAdapter = new IntroducedViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setClipToPadding(false);
        circlePageIndicator.setViewPager(viewPager);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                mPresenter.onButtonLoginClick();
                break;
            case R.id.btnRegister:
                mPresenter.onButtonRegisterClick();
                break;
        }
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
    public void showResultLogin(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showRegisterActivity() {
        Intent intent1 = new Intent(this, RegisterActivity.class);
        startActivity(intent1);
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
