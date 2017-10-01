package com.example.viet.workup.ui.board;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.BoardUserActivity;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.ui.board.option.add_member.MemberAddingDialogFragment;
import com.example.viet.workup.ui.board.option.background.BackgroundDialogFragment;
import com.example.viet.workup.ui.board.option.list_card.ListAddingDialogFragment;
import com.example.viet.workup.ui.board.option.member.CardMemberDialogFragment;
import com.example.viet.workup.ui.custom_view.CustomViewPager;
import com.example.viet.workup.utils.ApplicationUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.STAR_BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.UID;

public class BoardActivity extends BaseActivity implements BoardMvpView, View.OnClickListener {
    private static final String TAG = "BoardUserActivity";

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.fabAddList)
    FloatingActionButton fabAddList;
    @BindView(R.id.fabAddMember)
    FloatingActionButton fabAddMember;
    @BindView(R.id.fabBackground)
    FloatingActionButton fabBackground;
    @BindView(R.id.fabMenu)
    FloatingActionsMenu fabMenu;
    @BindView(R.id.recyclerViewActivity)
    RecyclerView recyclerViewActivity;
    @BindView(R.id.boardLayout)
    CoordinatorLayout coordinatorLayout;
    @Inject
    BoardPresenter<BoardMvpView> mPresenter;
    //
    private String mUidBoard;
    private String mBoardKey;
    private boolean isStar;
    //
    private ArrayList<CardList> mArrCardList = new ArrayList<>();
    private ArrayList<String> mArrCardListKey = new ArrayList<>();
    private ArrayList<BoardUserActivity> mArrBoardActivity = new ArrayList<>();
    private BoardViewPagerAdapter mBoardViewPagerAdapter;
    private BoardActivityAdapter mBoardActivityAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_board);
        mUidBoard = getIntent().getStringExtra(UID);
        mBoardKey = getIntent().getStringExtra(BOARD);
        isStar = getIntent().getBooleanExtra(STAR_BOARD, false);
        //
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        initViews();
        initToolbar();
        receiveData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            if (fabMenu.isExpanded()) {
                Rect outRect = new Rect();
                fabMenu.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    fabMenu.collapse();
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private void receiveData() {

        try {
            mPresenter.onReceiveTitle(mUidBoard, mBoardKey, isStar);
            mPresenter.onReceiveData(mBoardKey);
            mBoardActivityAdapter.clearItem();
            mPresenter.onReceiveBackground(mUidBoard, mBoardKey, isStar);
            mPresenter.onReceiveMember(mBoardKey);
        } catch (Exception e) {
            Toast.makeText(this, "Data is not created", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void initViews() {
        recyclerViewActivity.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewActivity.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBoardActivityAdapter = new BoardActivityAdapter(mArrBoardActivity);
        recyclerViewActivity.setAdapter(mBoardActivityAdapter);
        fabAddList.setOnClickListener(this);
        fabAddMember.setOnClickListener(this);
        fabBackground.setOnClickListener(this);
        mBoardViewPagerAdapter = new BoardViewPagerAdapter(getSupportFragmentManager(), mBoardKey, mArrCardList, mArrCardListKey);
        viewPager.setParallaxEnabled(true);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setClipToPadding(false);
        viewPager.setAdapter(mBoardViewPagerAdapter);
        viewPager.set_max_pages(20);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setBackgroundAsset(ApplicationUtils.rawId(0));
        if (TextUtils.isEmpty(mUidBoard) || TextUtils.isEmpty(mBoardKey)) {
            return;
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, android.R.drawable.ic_delete, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mPresenter.onReceiveActivity(mBoardKey);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mPresenter.removeActivityEvent(mBoardKey);
                mBoardActivityAdapter.clearItem();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Log.i(TAG, slideOffset + "");
                coordinatorLayout.setTranslationX(drawerView.getWidth() * (-slideOffset));
                drawerLayout.bringChildToFront(drawerView);
                drawerView.requestLayout();
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board_option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_show) {
            drawerLayout.openDrawer(Gravity.RIGHT, true);
        } else if (id == R.id.item_delete) {
            ApplicationUtils.buildConfirmDialog(this, "Are you sure want to delete",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (AccountManager.getsInstance().getCurrentUser().getUid().equals(mUidBoard)) {
                                mPresenter.onDeleteBoard(mUidBoard, mBoardKey, isStar);
                            } else {
                                Toast.makeText(BoardActivity.this, "Member can not delete board", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).show();
        } else if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.item_member) {
            CardMemberDialogFragment dialogFragment = CardMemberDialogFragment.newInstance(mBoardKey, mUidBoard, isStar);
            dialogFragment.show(getSupportFragmentManager(), "");
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showArrCardList(CardList cardList) {
        mBoardViewPagerAdapter.addItem(cardList);
    }

    @Override
    public void showArrActivity(BoardUserActivity boardUserActivity) {
        recyclerViewActivity.smoothScrollToPosition(0);
        mBoardActivityAdapter.addItem(boardUserActivity);
    }

    @Override
    public void hideCardList(String cardListKey) {
        mBoardViewPagerAdapter.removeItem(mArrCardListKey.indexOf(cardListKey));
    }

    @Override
    public void showBackground(int id) {
        viewPager.setBackgroundAsset(id);
    }

    @Override
    public void showTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void finishAcitivity() {
        Toast.makeText(this, "No board data !", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showAddingListDialog() {
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
    public void onClick(View view) {
        if (view.getId() == R.id.fabAddList) {
            if (mArrCardList.size() >= 20) {
                Toast.makeText(this, "Cant add more list", Toast.LENGTH_SHORT).show();
                return;
            }
            ListAddingDialogFragment addingDialogFragment = ListAddingDialogFragment.newInstance(mBoardKey);
            addingDialogFragment.show(getSupportFragmentManager(), "");
        } else if (view.getId() == R.id.fabAddMember) {
            if (mUidBoard.equals(AccountManager.getsInstance().getCurrentUser().getUid())) {
                MemberAddingDialogFragment dialogFragment = MemberAddingDialogFragment.newInstance(mUidBoard, mBoardKey, isStar);
                dialogFragment.show(getSupportFragmentManager(), "");
            } else {
                Toast.makeText(this, "Cant add member", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.fabBackground) {
            BackgroundDialogFragment backgroundDialogFragment = BackgroundDialogFragment.newInstance(mUidBoard, mBoardKey, isStar);
            backgroundDialogFragment.show(getSupportFragmentManager(), "");
        }
        fabMenu.collapse();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
