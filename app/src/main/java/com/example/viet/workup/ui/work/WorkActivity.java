package com.example.viet.workup.ui.work;

import android.app.DatePickerDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
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
import com.example.viet.workup.ui.work.label.LabelDialogFragment;
import com.example.viet.workup.ui.work.work_list.WorkListDialogFragment;
import com.example.viet.workup.utils.CalendarUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

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
    @BindView(R.id.fabAddLabel)
    FloatingActionButton fabAddLabel;
    @BindView(R.id.fabAddMember)
    FloatingActionButton fabAddMember;
    @BindView(R.id.fabAddDueDate)
    FloatingActionButton fabAddDueDate;
    @BindView(R.id.fabAddWorkList)
    FloatingActionButton fabAddWorkList;
    @BindView(R.id.fabMenu)
    FloatingActionsMenu fabMenu;

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
        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        fabAddLabel.setOnClickListener(this);
        fabAddMember.setOnClickListener(this);
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
        workListRecyclerViewAdapter = new WorkListRecyclerViewAdapter(arrWorkList);
        recyclerViewWorkList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewWorkList.setAdapter(workListRecyclerViewAdapter);
        //
        commentListRecyclerViewAdapter = new CommentListRecyclerViewAdapter(arrComment);
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewComment.setAdapter(commentListRecyclerViewAdapter);
        //
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
    public void showLabel(Label label) {
        labelRecyclerViewAdapter.addItem(label);
    }

    @Override
    public void showMemeber(UserInfo userInfo) {
        memberRecyclerViewAdapter.addItem(userInfo);
    }

    @Override
    public void showWordList(WorkList workList) {
        workListRecyclerViewAdapter.addItem(workList);
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
    public void showComment(Comment comment) {
        commentListRecyclerViewAdapter.addItem(comment);
    }

    @Override
    public void showDueDate(DueDate dueDate) {
        Toast.makeText(this, dueDate.getDay() + "/" + dueDate.getMonth() + "/" + dueDate.getYeah(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fabAddLabel) {
            LabelDialogFragment.newInstance(mCardKey).show(getSupportFragmentManager(), "");
        } else if (id == R.id.fabAddMember) {

        } else if (id == R.id.fabAddDueDate) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                }
            }, CalendarUtils.getYear(), CalendarUtils.getMonth(), CalendarUtils.getDay());
            dialog.show();
        } else if (id == R.id.fabAddWorkList) {
            WorkListDialogFragment.newInstance(mCardKey).show(getSupportFragmentManager(), "");
        }
        fabMenu.collapse();
    }
}
