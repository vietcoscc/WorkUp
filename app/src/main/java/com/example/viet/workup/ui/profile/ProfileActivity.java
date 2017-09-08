package com.example.viet.workup.ui.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;

public class ProfileActivity extends BaseActivity implements ProfileMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }


}
