package com.example.viet.workup.ui.board;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.viet.workup.R;
import com.example.viet.workup.ui.custom_view.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoardActivity extends AppCompatActivity {
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        BoardViewPagerAdapter adapter = new BoardViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.set_max_pages(10);
        viewPager.setBackgroundAsset(R.raw.as);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(5);
//        viewPager.setPageMargin(-50);
    }
}
