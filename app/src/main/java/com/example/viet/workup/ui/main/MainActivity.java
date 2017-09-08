package com.example.viet.workup.ui.main;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Board;
import com.example.viet.workup.ui.main.creating.BoardCreatingDialog;
import com.example.viet.workup.ui.main.menu.BoardOptionMenu;

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

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private AccountManager mAccountManager = AccountManager.getsInstance();
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerViewMyBoard)
    RecyclerView recyclerViewMyBoard;
    @BindView(R.id.recyclerViewStarBoard)
    RecyclerView recyclerViewStarBoard;

    @Inject
    MainPresenter<MainMvpView> mPresenter;

    private ArrayList<Board> arrMyBoard = new ArrayList<>();
    private MyboardRecyclerViewAdapter myBoardAdapter;

    private ArrayList<Board> arrStarBoard = new ArrayList<>();
    private MyboardRecyclerViewAdapter starBoardAdapter;

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

    }

    private void initViews() {
        fab.setOnClickListener(this);

        recyclerViewMyBoard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myBoardAdapter = new MyboardRecyclerViewAdapter(arrMyBoard, true);
        myBoardAdapter.setmOnItemLongClickListener(new Listener.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                showBoardOptionMenu(view, position);
            }
        });
        recyclerViewMyBoard.setAdapter(myBoardAdapter);

        recyclerViewStarBoard.setLayoutManager(new GridLayoutManager(this, 2));
        starBoardAdapter = new MyboardRecyclerViewAdapter(arrStarBoard, false);
        recyclerViewStarBoard.setAdapter(starBoardAdapter);
    }

    private void initToolbar() {
        toolbar.setTitle(" " + mAccountManager.getCurrentUser().getDisplayName());
        setSupportActionBar(toolbar);
        getDrawableObservable(mAccountManager.getCurrentUser().getPhotoUrl().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        getSupportActionBar().setIcon(drawable);
                    }
                });

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            mPresenter.onFloatingActionButtonClick();
        }
    }

    @Override
    public void showDialogBoard() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
//        builder.setMessage("Create board");
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_board_creating, null);
//        builder.setView(view);
//        final EditText edtName = view.findViewById(R.id.edtName);
//        final Spinner spinner = view.findViewById(R.id.spinner);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"No group"});
//        spinner.setAdapter(arrayAdapter);
//        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                showProgress();
//                mPresenter.onCreateBoardButtonClick(edtName.getText().toString(), spinner.getSelectedItem().toString());
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.create().show();
//        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                edtName.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputMethodManager.showSoftInput(edtName, InputMethodManager.SHOW_IMPLICIT);
//                    }
//                });
//            }
//        });
//        edtName.requestFocus();
        BoardCreatingDialog dialog = BoardCreatingDialog.newInstance();
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showStarBoardReceived(Board board) {
        arrStarBoard.add(board);
        starBoardAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUnstarBoardReceived(Board board) {
        arrMyBoard.add(board);
        myBoardAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBoardOptionMenu(View view, int position) {
        BoardOptionMenu optionMenu = new BoardOptionMenu(MainActivity.this, view,recyclerViewMyBoard , arrMyBoard, position);
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
            public Drawable call() throws Exception {
                URL u = new URL(url);
                URLConnection connection = u.openConnection();
                InputStream inputStream = connection.getInputStream();
                Drawable drawable = new BitmapDrawable(inputStream);
                return drawable;
            }
        });
    }
}
