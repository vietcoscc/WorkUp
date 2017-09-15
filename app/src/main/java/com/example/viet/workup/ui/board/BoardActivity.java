package com.example.viet.workup.ui.board;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.ui.board.adding.AddingDialogFragment;
import com.example.viet.workup.ui.custom_view.CustomViewPager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;

public class BoardActivity extends BaseActivity implements BoardMvpView {
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @Inject
    BoardPresenter<BoardMvpView> mPresenter;

    private String mBoardKey;
    private ArrayList<CardList> arrCardList = new ArrayList<>();
    private BoardViewPagerAdapter mBoardViewPagerAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        receiveData();
        initToolbar();
        initViews();
    }

    private void receiveData() {
        mBoardKey = getIntent().getStringExtra(BOARD);
        mPresenter.onReceiveData(mBoardKey);
    }

    private void initViews() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onFlatingActionButtonClick();
            }
        });
        mBoardViewPagerAdapter = new BoardViewPagerAdapter(getSupportFragmentManager(), mBoardKey, arrCardList);
        viewPager.setAdapter(mBoardViewPagerAdapter);
        viewPager.set_max_pages(10);
        viewPager.setBackgroundAsset(R.raw.as);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(20);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void showArrCardList(CardList cardList) {
        arrCardList.add(cardList);
        mBoardViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAddingListDialog() {
        AddingDialogFragment addingDialogFragment = AddingDialogFragment.newInstance(mBoardKey);
        addingDialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
