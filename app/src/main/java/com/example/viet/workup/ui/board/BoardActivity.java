package com.example.viet.workup.ui.board;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.model.CardList;
import com.example.viet.workup.ui.board.add_member.MemberAddingDialogFragment;
import com.example.viet.workup.ui.board.background.BackgroundDialogFragment;
import com.example.viet.workup.ui.board.list_card.ListAddingDialogFragment;
import com.example.viet.workup.ui.board.member.CardMemberDialogFragment;
import com.example.viet.workup.ui.custom_view.CustomViewPager;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.STAR_BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.UID;

public class BoardActivity extends BaseActivity implements BoardMvpView, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "BoardActivity";

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
    @BindView(R.id.fabViewMember)
    FloatingActionButton fabViewMember;
    @BindView(R.id.fabAuthority)
    FloatingActionButton fabAuthority;
    @BindView(R.id.fabBackground)
    FloatingActionButton fabBackground;
    @BindView(R.id.fabMenu)
    FloatingActionsMenu fabMenu;
    @BindView(R.id.nav)
    NavigationView nav;

    @Inject
    BoardPresenter<BoardMvpView> mPresenter;
    //
    private String mUidBoard;
    private String mBoardKey;
    private boolean isStar;
    //
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
        try {
            initViews();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        mUidBoard = getIntent().getStringExtra(UID);
        mBoardKey = getIntent().getStringExtra(BOARD);
        isStar = getIntent().getBooleanExtra(STAR_BOARD, false);
        Log.i(TAG, mUidBoard + "");
        Log.i(TAG, mBoardKey + "");
        Log.i(TAG, isStar + "");
        try {
            mPresenter.onReceiveData(mBoardKey);
        } catch (Exception e) {
            Toast.makeText(this, "Data is not created", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void initViews() throws MalformedURLException {

        nav.setNavigationItemSelectedListener(this);
        fabAddList.setOnClickListener(this);
        fabAddMember.setOnClickListener(this);
        fabViewMember.setOnClickListener(this);
        fabAuthority.setOnClickListener(this);
        fabBackground.setOnClickListener(this);
        Field[] ID_Fields = R.raw.class.getFields();
        mBoardViewPagerAdapter = new BoardViewPagerAdapter(getSupportFragmentManager(), mBoardKey, arrCardList);

        viewPager.setParallaxEnabled(true);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setClipToPadding(false);
        try {
            viewPager.setBackgroundAsset(ID_Fields[1].getInt(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(mBoardViewPagerAdapter);
        viewPager.setOffscreenPageLimit(20);
        if (TextUtils.isEmpty(mUidBoard) || TextUtils.isEmpty(mBoardKey)) {
            return;
        }
        mPresenter.onReceiveBackground(mUidBoard, mBoardKey, isStar);

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
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void showArrCardList(CardList cardList) {
        mBoardViewPagerAdapter.addItem(cardList);
    }

    @Override
    public void hideCardList(String cardListKey) {
        Toast.makeText(this, cardListKey, Toast.LENGTH_SHORT).show();
        getCardListObservable(cardListKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mBoardViewPagerAdapter.removeItem(integer);
                    }
                });
    }

    @Override
    public void showBackground(int id) {
        viewPager.setBackgroundAsset(id);
    }

    @Override
    public void showAddingListDialog() {

    }

    public Observable<Integer> getCardListObservable(final String cardListKey) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int size = arrCardList.size();
                for (int i = 0; i < size; i++) {
                    if (cardListKey.trim().equals(arrCardList.get(i).getKey().trim())) {
                        return i;
                    }
                }
                return -1;
            }
        });
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
            ListAddingDialogFragment addingDialogFragment = ListAddingDialogFragment.newInstance(mBoardKey);
            addingDialogFragment.show(getSupportFragmentManager(), "");
        } else if (view.getId() == R.id.fabAddMember) {
            MemberAddingDialogFragment dialogFragment = MemberAddingDialogFragment.newInstance(mUidBoard, mBoardKey, isStar);
            dialogFragment.show(getSupportFragmentManager(), "");
        } else if (view.getId() == R.id.fabViewMember) {
            CardMemberDialogFragment dialogFragment = CardMemberDialogFragment.newInstance(mBoardKey, mUidBoard);
            dialogFragment.show(getSupportFragmentManager(), "");
        } else if (view.getId() == R.id.fabAuthority) {

        } else if (view.getId() == R.id.fabBackground) {
            BackgroundDialogFragment backgroundDialogFragment = BackgroundDialogFragment.newInstance(mUidBoard, mBoardKey, isStar);
            backgroundDialogFragment.show(getSupportFragmentManager(), "");
        }
        fabMenu.collapse();
    }

    @Override
    public boolean onNavigationItemSelected(@android.support.annotation.NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_member) {

        } else if (id == R.id.item_back_ground) {

        }
        drawerLayout.closeDrawer(Gravity.RIGHT, false);
        return false;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
