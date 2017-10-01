package com.example.viet.workup.ui.main;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.example.viet.workup.service.NotificationService;
import com.example.viet.workup.ui.board.BoardActivity;
import com.example.viet.workup.ui.introduced.IntroducedActivity;
import com.example.viet.workup.ui.main.board.BoardCreatingDialog;
import com.example.viet.workup.ui.main.menu.BoardOptionMenu;
import com.example.viet.workup.ui.profile.ProfileActivity;
import com.example.viet.workup.utils.ApplicationUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.STAR_BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.UID;

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private AccountManager mAccountManager = AccountManager.getsInstance();
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerViewUnstarBoard)
    RecyclerView recyclerViewUnstarBoard;
    @BindView(R.id.recyclerViewStarBoard)
    RecyclerView recyclerViewStarBoard;
    @BindView(R.id.recyclerViewOtherrBoard)
    RecyclerView recyclerViewOtherrBoard;
    @Inject
    MainPresenter<MainMvpView> mPresenter;
    //
    private ArrayList<Board> mArrUnstarBoard = new ArrayList<>();
    private ArrayList<String> mArrUnstarBoardKey = new ArrayList<>();
    private MyboardRecyclerViewAdapter mUnstarBoardAdapter;
    private ArrayList<Board> mArrStarBoard = new ArrayList<>();
    private ArrayList<String> mArrStarBoardKey = new ArrayList<>();
    private MyboardRecyclerViewAdapter mStarBoardAdapter;
    private ArrayList<Board> mArrOtherBoard = new ArrayList<>();
    private ArrayList<String> mArrOtherBoardKey = new ArrayList<>();
    private MyboardRecyclerViewAdapter mOtherBoardAdapter;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        initToolbar();
        initViews();
        mPresenter.onAttach(this);
        mPresenter.onReceiveData();
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
        ApplicationUtils.setInApp(true);
    }

    private void initViews() {
        fab.setOnClickListener(this);
        //recyclerViewUnstarBoard
        recyclerViewUnstarBoard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ViewCompat.setNestedScrollingEnabled(recyclerViewUnstarBoard, false);
        mUnstarBoardAdapter = new MyboardRecyclerViewAdapter(mArrUnstarBoard, mArrUnstarBoardKey, true);
        mUnstarBoardAdapter.setmOnItemLongClickListener(new Listener.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
//                showUnstarBoardOptionMenu(view, position);
            }
        });
        mUnstarBoardAdapter.setmOnItemClickListenter(new Listener.OnItemClickListenter() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                intent.putExtra(UID, mArrUnstarBoard.get(position).getParentKey());
                intent.putExtra(BOARD, mArrUnstarBoard.get(position).getKey());
                intent.putExtra(STAR_BOARD, mArrUnstarBoard.get(position).isStar());
                startActivity(intent);
            }
        });
        recyclerViewUnstarBoard.setAdapter(mUnstarBoardAdapter);
        // recyclerViewStarBoard
        recyclerViewStarBoard.setLayoutManager(new GridLayoutManager(this, 2));
        ViewCompat.setNestedScrollingEnabled(recyclerViewStarBoard, false);
        mStarBoardAdapter = new MyboardRecyclerViewAdapter(mArrStarBoard, mArrStarBoardKey, false);
        mStarBoardAdapter.setmOnItemLongClickListener(new Listener.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
//                showStarBoardOptionMene(view, position);
            }
        });
        mStarBoardAdapter.setmOnItemClickListenter(new Listener.OnItemClickListenter() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                intent.putExtra(UID, mArrStarBoard.get(position).getParentKey());
                intent.putExtra(BOARD, mArrStarBoard.get(position).getKey());
                intent.putExtra(STAR_BOARD, mArrStarBoard.get(position).isStar());
                startActivity(intent);
            }
        });
        recyclerViewStarBoard.setAdapter(mStarBoardAdapter);
        //
        recyclerViewOtherrBoard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ViewCompat.setNestedScrollingEnabled(recyclerViewOtherrBoard, false);
        mOtherBoardAdapter = new MyboardRecyclerViewAdapter(mArrOtherBoard, mArrOtherBoardKey, true);
        mOtherBoardAdapter.setmOnItemClickListenter(new Listener.OnItemClickListenter() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                intent.putExtra(UID, mArrOtherBoard.get(position).getParentKey());
                intent.putExtra(BOARD, mArrOtherBoard.get(position).getKey());
                intent.putExtra(STAR_BOARD, mArrOtherBoard.get(position).isStar());
                startActivity(intent);
            }
        });
        recyclerViewOtherrBoard.setAdapter(mOtherBoardAdapter);

    }

    private void initToolbar() {
        String displayName = mAccountManager.getCurrentUser().getDisplayName();
        if (displayName == null || displayName.isEmpty()) {
            toolbar.setTitle(R.string.app_name);
        } else {
            toolbar.setTitle(" " + displayName);
        }
        setSupportActionBar(toolbar);
        Uri photoUrl = mAccountManager.getCurrentUser().getPhotoUrl();
        if (photoUrl == null || photoUrl.toString().isEmpty()) {
            getSupportActionBar().setIcon(R.drawable.man);
        } else {
            getDrawableObservable(photoUrl.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Drawable>() {
                        @Override
                        public void accept(Drawable drawable) throws Exception {
                            getSupportActionBar().setIcon(drawable);
                        }
                    });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mPresenter.onOptionMenuActionLogoutClick();
        } else if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            mPresenter.onFloatingActionButtonClick();
        }
    }

    @Override
    public void showDialogBoard() {
        BoardCreatingDialog dialog = BoardCreatingDialog.newInstance();
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showIntroduce() {
        Intent intent = new Intent(this, IntroducedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showStarBoardReceived(Board board) {
        mStarBoardAdapter.addItem(board);
    }

    @Override
    public void showUnstarBoardReceived(Board board) {
        mUnstarBoardAdapter.addItem(board);
    }

    @Override
    public void showOtherBoardReceived(Board board) {
        mOtherBoardAdapter.addItem(board);
    }

    @Override
    public void deleteStarBoardReceived(String board) {
        mStarBoardAdapter.removeItem(mArrStarBoardKey.indexOf(board));
    }

    @Override
    public void deleteUnstarBoardReceived(String board) {
        mUnstarBoardAdapter.removeItem(mArrUnstarBoardKey.indexOf(board));
    }

    @Override
    public void deleteOtherBoardReceived(String board) {
        mOtherBoardAdapter.removeItem(mArrOtherBoard.indexOf(board));
    }

    @Override
    public void removeOtherBoard(String boardUid, String otherBoardKey) {
        mOtherBoardAdapter.removeItem(mArrOtherBoardKey.indexOf(otherBoardKey));
    }

    @Override
    public void showUnstarBoardOptionMenu(View view, int position) {
        BoardOptionMenu optionMenu = new BoardOptionMenu(MainActivity.this, view, recyclerViewUnstarBoard, mArrUnstarBoard, position);
        optionMenu.show();
    }

    @Override
    public void showStarBoardOptionMene(View view, int position) {
        BoardOptionMenu optionMenu = new BoardOptionMenu(MainActivity.this, view, recyclerViewStarBoard, mArrStarBoard, position);
        optionMenu.show();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        mPresenter.onDetach();
        super.onDestroy();
    }

    public Observable<Drawable> getDrawableObservable(final String url) {
        return Observable.fromCallable(new Callable<Drawable>() {
            @Override
            public Drawable call() {
                try {
                    URL u = new URL(url);
                    URLConnection connection = u.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    Drawable drawable = new BitmapDrawable(inputStream);
                    return drawable;
                } catch (Exception e) {
                    Drawable drawable = getResources().getDrawable(R.drawable.man);
                    return drawable;
                }

            }
        });
    }

}
