package com.example.viet.workup.ui.work;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
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
import com.example.viet.workup.utils.CalendarUtils;
import com.example.viet.workup.utils.FireBaseStorageUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD_KEY;

public class WorkActivity extends BaseActivity implements WorkMvpView, View.OnClickListener {
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
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private LabelRecyclerViewAdapter labelRecyclerViewAdapter;
    private MemberRecyclerViewAdapter memberRecyclerViewAdapter;
    private WorkListRecyclerViewAdapter workListRecyclerViewAdapter;
    private CommentListRecyclerViewAdapter commentListRecyclerViewAdapter;

    private ArrayList<Label> arrLabel = new ArrayList<>();
    private ArrayList<UserInfo> arrUserInfo = new ArrayList<>();
    private ArrayList<WorkList> arrWorkList = new ArrayList<>();
    private ArrayList<Comment> arrComment = new ArrayList<>();
    private String mCardKey;

    @Inject
    WorkPresenter<WorkMvpView> mPresenter;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().isEmpty()) {
                ivComment.setVisibility(View.GONE);
            } else {
                ivComment.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        mCardKey = getIntent().getStringExtra(CARD_KEY);
        initViews();
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
        collapsingToolbarLayout.setTitleEnabled(false);
        //
        edtComment.addTextChangedListener(textWatcher);
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
        workListRecyclerViewAdapter = new WorkListRecyclerViewAdapter(arrWorkList, mCardKey);
        recyclerViewWorkList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewWorkList.setAdapter(workListRecyclerViewAdapter);
        //
        commentListRecyclerViewAdapter = new CommentListRecyclerViewAdapter(arrComment);
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewComment.setAdapter(commentListRecyclerViewAdapter);
        //
        mPresenter.onReceiveCoverImage(mCardKey);
        mPresenter.onReceiveTitle(mCardKey);
        mPresenter.onReceiveDescription(mCardKey);
        mPresenter.onReceiveLabel(mCardKey);
        mPresenter.onReceiveMember(mCardKey);
        mPresenter.onReceiveWorkList(mCardKey);
        mPresenter.onReceiveComment(mCardKey);
        mPresenter.onReveiveDueDate(mCardKey);
        //
        ViewCompat.setNestedScrollingEnabled(recyclerViewLabel, false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewMember, false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewWorkList, false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewComment, false);
    }


    @Override
    public void showCoverImage(String url) {
        if (url == null || url.isEmpty()) {
            ivCover.setVisibility(View.GONE);
        } else {
            ivCover.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(url)
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
        Toast.makeText(this, "Card list has been removed", Toast.LENGTH_SHORT).show();
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
        }
        fabMenu.collapse();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

}
