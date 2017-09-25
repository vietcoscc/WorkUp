package com.example.viet.workup.ui.work;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.model.WorkList;
import com.example.viet.workup.ui.board.card.LabelRecyclerViewAdapter;
import com.example.viet.workup.ui.board.card.MemberRecyclerViewAdapter;
import com.example.viet.workup.ui.image.main.ImageActivity;
import com.example.viet.workup.ui.work.due_date.DueDateDialog;
import com.example.viet.workup.ui.work.label.LabelDialogFragment;
import com.example.viet.workup.ui.work.work_list.WorkListDialogFragment;
import com.example.viet.workup.utils.ApplicationUtils;
import com.example.viet.workup.utils.CalendarUtils;
import com.example.viet.workup.utils.DataUtils;
import com.example.viet.workup.utils.FireBaseStorageUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD_KEY;

public class WorkActivity extends BaseActivity implements WorkMvpView, View.OnClickListener {
    private AccountManager mAccountManager = AccountManager.getsInstance();
    @BindView(R.id.recyclerViewLabel)
    RecyclerView recyclerViewLabel;
    @BindView(R.id.recyclerViewMember)
    RecyclerView recyclerViewMember;
    @BindView(R.id.recyclerViewWorkList)
    RecyclerView recyclerViewWorkList;
    @BindView(R.id.recyclerViewAttachment)
    RecyclerView recyclerViewAttachment;
    @BindView(R.id.recyclerViewComment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivMember)
    ImageView ivMember;
    @BindView(R.id.ivLabel)
    ImageView ivLabel;
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvDueDate)
    TextView tvDueDate;
    @BindView(R.id.edtComment)
    EditText edtComment;
    @BindView(R.id.ivComment)
    ImageView ivComment;
    @BindView(R.id.fabAddLabel)
    FloatingActionButton fabAddLabel;
    @BindView(R.id.fabAddCover)
    FloatingActionButton fabAddcover;
    @BindView(R.id.fabAddDueDate)
    FloatingActionButton fabAddDueDate;
    @BindView(R.id.fabAddWorkList)
    FloatingActionButton fabAddWorkList;
    @BindView(R.id.fabMenu)
    FloatingActionsMenu fabMenu;
    @BindView(R.id.ivCheck)
    ImageView ivcheck;
    @BindView(R.id.edtEdit)
    EditText edtEdit;
    @BindView(R.id.ivCheckDes)
    ImageView ivcheckDes;
    @BindView(R.id.edtEditDes)
    EditText edtEditDes;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private LabelRecyclerViewAdapter labelRecyclerViewAdapter;
    private MemberRecyclerViewAdapter memberRecyclerViewAdapter;
    private WorkListRecyclerViewAdapter workListRecyclerViewAdapter;
    private CommentListRecyclerViewAdapter commentListRecyclerViewAdapter;

    private ArrayList<Label> arrLabel = new ArrayList<>();
    private ArrayList<UserInfo> arrUserInfo = new ArrayList<>();
    private ArrayList<WorkList> arrWorkList = new ArrayList<>();
    private ArrayList<String> arrWorkListKey = new ArrayList<>();
    private ArrayList<Comment> arrComment = new ArrayList<>();
    private String mCardKey;

    @Inject
    WorkPresenter<WorkMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_work);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        mCardKey = getIntent().getStringExtra(CARD_KEY);
        initViews();
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

    private void initViews() {
        ivcheckDes.setOnClickListener(this);
        edtEditDes.addTextChangedListener(ApplicationUtils.getTextWatcher(ivcheckDes));
        edtEditDes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tvDescription.setOnClickListener(this);
        //
        ivcheck.setOnClickListener(this);
        edtEdit.addTextChangedListener(ApplicationUtils.getTextWatcher(ivcheck));
        edtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        toolbar.setOnClickListener(this);
        //
        collapsingToolbarLayout.setTitleEnabled(false);
        //
        edtComment.addTextChangedListener(ApplicationUtils.getTextWatcher(ivComment));
        ivComment.setOnClickListener(this);
        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        fabAddLabel.setOnClickListener(this);
        fabAddcover.setOnClickListener(this);
        fabAddDueDate.setOnClickListener(this);
        fabAddWorkList.setOnClickListener(this);
        //
        labelRecyclerViewAdapter = new LabelRecyclerViewAdapter(arrLabel);
        recyclerViewLabel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLabel.setAdapter(labelRecyclerViewAdapter);
        //
        memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(arrUserInfo);
        recyclerViewMember.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMember.setAdapter(memberRecyclerViewAdapter);
        //
        workListRecyclerViewAdapter = new WorkListRecyclerViewAdapter(arrWorkList, arrWorkListKey, mCardKey);

        recyclerViewWorkList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewWorkList.setAdapter(workListRecyclerViewAdapter);
        //
        commentListRecyclerViewAdapter = new CommentListRecyclerViewAdapter(arrComment);
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewComment.setAdapter(commentListRecyclerViewAdapter);
        //

        //
        ViewCompat.setNestedScrollingEnabled(recyclerViewLabel, false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewMember, false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewWorkList, false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewComment, false);
    }

    private void receiveData() {
        mPresenter.onReceiveCoverImage(mCardKey);
        mPresenter.onReceiveTitle(mCardKey);
        mPresenter.onReceiveDescription(mCardKey);
        mPresenter.onReceiveLabel(mCardKey);
        mPresenter.onReceiveMember(mCardKey);
        mPresenter.onReceiveWorkList(mCardKey);
        mPresenter.onReceiveComment(mCardKey);
        mPresenter.onReveiveDueDate(mCardKey);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.work_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showCoverImage(String url) {
        if (url == null || url.isEmpty()) {
            ivCover.setVisibility(View.GONE);
        } else {
            ivCover.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(url)
//                    .resize(500,300)
                    .centerCrop()
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(ivCover);
        }
    }

    @Override
    public void showLabel(final Label label) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                labelRecyclerViewAdapter.addItem(label);
            }
        });

    }

    @Override
    public void showMemeber(final UserInfo userInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                memberRecyclerViewAdapter.addItem(userInfo);
            }
        });

    }

    @Override
    public void showWordList(final WorkList workList) {
        workListRecyclerViewAdapter.addItem(workList);
    }

    @Override
    public void deleteWorkList(String key) {
        workListRecyclerViewAdapter.removeItem(arrWorkListKey.indexOf(key));
    }

    @Override
    public void changeWorkList(WorkList workList) {
        workListRecyclerViewAdapter.changeItem(workList, arrWorkListKey.indexOf(workList.getKey()));
    }

    @Override
    public void showComment(final Comment comment) {
        commentListRecyclerViewAdapter.addItem(comment);
    }

    @Override
    public void showAttachment(String attachFile) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_delete) {
            Dialog dialog = ApplicationUtils.buildConfirmDialog(this, "Are you sure want to delete  ?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(WorkActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                    mPresenter.onDeleteCard(mCardKey, toolbar.getTitle().toString());
                }
            });
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showDueDate(final DueDate dueDate) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(WorkActivity.this, dueDate.toString(), Toast.LENGTH_SHORT).show();
                tvDueDate.setText(dueDate.toString());
                Date date = new Date(dueDate.getYear(), dueDate.getMonth(), dueDate.getDay());
                Date currentDate = CalendarUtils.getCurrentDay();
                if (currentDate.before(date)) {
                    tvDueDate.setTextColor(Color.GREEN);
                } else {
                    tvDueDate.setTextColor(Color.RED);
                }
            }
        });

    }

    @Override
    public void showTitle(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    @Override
    public void showDes(String des) {
        tvDescription.setText(des);
    }

    @Override
    public void resetTextComment() {
        edtComment.setText("");
    }

    @Override
    public void finishActivity() {
        Toast.makeText(this, "This card has been removed", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fabAddLabel) {
            LabelDialogFragment.newInstance(mCardKey).show(getSupportFragmentManager(), "");
        } else if (id == R.id.fabAddDueDate) {
            final DueDateDialog dialog = new DueDateDialog(this, CalendarUtils.getYear(),
                    CalendarUtils.getMonth(),
                    CalendarUtils.getDay(),
                    mCardKey);
            dialog.show();
        } else if (id == R.id.fabAddWorkList) {
            WorkListDialogFragment.newInstance(mCardKey).show(getSupportFragmentManager(), "");
        } else if (id == R.id.ivComment) {
            InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(edtComment.getWindowToken(), 0);
            mPresenter.onAddComment(mCardKey, edtComment.getText().toString().trim());
        } else if (id == R.id.fabAddCover) {
            Intent intent = new Intent(this, ImageActivity.class);
            FireBaseStorageUtils.setCurrentCardKey(mCardKey);
            startActivity(intent);
        } else if (id == R.id.toolbar) {
            edtEdit.setText(toolbar.getTitle());
            edtEdit.setVisibility(View.VISIBLE);
            ivcheck.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
            edtEdit.requestFocus();
        } else if (id == R.id.ivCheck) {
            if (DataUtils.isCardTitleValid(edtEdit.getText().toString())) {
                edtEdit.setVisibility(View.GONE);
                ivcheck.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(edtEdit.getWindowToken(), 0);
                mPresenter.onChangeTitle(mCardKey, edtEdit.getText().toString());
            } else {
                Toast.makeText(this, "Title invalid", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.tvDescription) {
            edtEditDes.setText(tvDescription.getText());
            edtEditDes.setVisibility(View.VISIBLE);
            ivcheckDes.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.GONE);
            edtEditDes.requestFocus();
        } else if (id == R.id.ivCheckDes) {
            if (DataUtils.isCardDescriptionValid(edtEditDes.getText().toString())) {
                edtEditDes.setVisibility(View.GONE);
                ivcheckDes.setVisibility(View.GONE);
                tvDescription.setVisibility(View.VISIBLE);
                InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(edtEditDes.getWindowToken(), 0);
                mPresenter.onChangeDescription(mCardKey, edtEditDes.getText().toString());
            } else {
                Toast.makeText(this, "Description invalid", Toast.LENGTH_SHORT).show();
            }
        }
        fabMenu.collapse();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

}
