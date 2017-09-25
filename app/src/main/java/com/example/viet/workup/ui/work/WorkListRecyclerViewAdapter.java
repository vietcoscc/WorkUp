package com.example.viet.workup.ui.work;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.model.Task;
import com.example.viet.workup.model.WorkList;
import com.example.viet.workup.utils.ApplicationUtils;
import com.example.viet.workup.utils.DataUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrTaskListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrWorkListRef;

/**
 * Created by viet on 13/09/2017.
 */

public class WorkListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    private static final String TAG = "WorkListRecyclerViewAdapter";

    private ArrayList<WorkList> arrWorkList;
    private ArrayList<String> arrWorkListKey;
    private Context mContext;
    private String mCardKey;

    public WorkListRecyclerViewAdapter(ArrayList<WorkList> arrWorkList, ArrayList<String> arrWorkListKey, String cardKey) {
        this.arrWorkList = arrWorkList;
        this.arrWorkListKey = arrWorkListKey;
        this.mCardKey = cardKey;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_list_recycler_view, parent, false);
        return new HeaderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        viewHolder.setdata(arrWorkList.get(position));
    }


    @Override
    public int getItemCount() {
        return arrWorkList.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        @BindView(R.id.tvHeader)
        TextView tvHeader;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;
        @BindView(R.id.edtTitle)
        EditText edttitle;
        @BindView(R.id.ivCheck)
        ImageView ivcheck;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.recyclerViewTask)
        RecyclerView recyclerViewTask;
        @BindView(R.id.tvProgress)
        TextView tvProgress;
        TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;
        ArrayList<Task> arrTask = new ArrayList<>();
        ArrayList<String> arrTaskKey = new ArrayList<>();
        long currentDone = 0;


        public HeaderViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(this);
            ivDelete.setOnClickListener(this);
            tvHeader.setOnClickListener(this);
            ivcheck.setOnClickListener(this);
            ivcheck.setVisibility(View.GONE);
            recyclerViewTask.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            ViewCompat.setNestedScrollingEnabled(recyclerViewTask, false);
            edttitle.addTextChangedListener(ApplicationUtils.getTextWatcher(ivcheck));
            edttitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            });

        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setKey(dataSnapshot.getKey());
                taskListRecyclerViewAdapter.addItem(task);
                float progress = (float) currentDone / arrTask.size();
                tvProgress.setText(currentDone + "/" + arrTask.size());
                progressBar.setProgress((int) (progress * 100));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setKey(dataSnapshot.getKey());
                taskListRecyclerViewAdapter.changeItem(task, arrTaskKey.indexOf(dataSnapshot.getKey()));
            }

            @Override
            public void onChildRemoved(final DataSnapshot dataSnapshot) {
                taskListRecyclerViewAdapter.removeItem(arrTaskKey.indexOf(dataSnapshot.getKey()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        public void setdata(final WorkList workList) {
            taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(arrTask, arrTaskKey, mCardKey, workList.getKey());
            taskListRecyclerViewAdapter.clearItem();
            recyclerViewTask.setAdapter(taskListRecyclerViewAdapter);

            arrTaskListRef(mCardKey, workList.getKey()).removeEventListener(childEventListener);
            arrTaskListRef(mCardKey, workList.getKey()).addChildEventListener(childEventListener);
            arrTaskListRef(mCardKey, workList.getKey()).orderByChild("hasDone").equalTo(true).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final long count = dataSnapshot.getChildrenCount();
                    currentDone = count;
                    float progress = (float) currentDone / arrTask.size();
                    tvProgress.setText(currentDone + "/" + arrTask.size());
                    progressBar.setProgress((int) (progress * 100));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            tvHeader.setText(workList.getTitle());
            taskListRecyclerViewAdapter.notifyDataSetChanged();
        }


        @Override
        public boolean onLongClick(View view) {

            return false;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.tvHeader) {
                edttitle.setText(tvHeader.getText());
                ivDelete.setVisibility(View.GONE);
                edttitle.setVisibility(View.VISIBLE);
                tvHeader.setVisibility(View.GONE);
                edttitle.requestFocus();

            } else if (id == R.id.ivDelete) {
                Dialog dialog = ApplicationUtils.buildConfirmDialog(mContext,
                        "Are you sure want to delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrWorkListRef(mCardKey).child(arrWorkListKey.get(getPosition())).setValue(null);
                                arrTaskListRef(mCardKey, arrWorkListKey.get(getPosition())).setValue(null);
                            }
                        });
                dialog.show();
            } else if (id == R.id.ivCheck) {
                if (DataUtils.isWorkListNameValid(edttitle.getText().toString())) {
                    ivDelete.setVisibility(View.VISIBLE);
                    tvHeader.setVisibility(View.VISIBLE);
                    ivcheck.setVisibility(View.GONE);
                    edttitle.setVisibility(View.GONE);
                    WorkList workList = new WorkList(edttitle.getText().toString(), 0, null);

                    arrWorkListRef(mCardKey).child(arrWorkListKey.get(getPosition())).setValue(workList);
                    InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(edttitle.getWindowToken(), 0);
                    edttitle.setText("");
                } else {
                    Toast.makeText(mContext, "Title invalid", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void addItem(WorkList workList) {
        arrWorkListKey.add(workList.getKey());
        arrWorkList.add(workList);
        notifyItemInserted(arrWorkList.size() - 1);
    }

    public void removeItem(int postition) {
        if (-1 < postition && postition < arrWorkList.size()) {
            arrWorkListKey.remove(postition);
            arrWorkList.remove(postition);
            notifyItemRemoved(postition);
        }
    }

    public void changeItem(WorkList workList, int position) {
        if (-1 < position && position < arrWorkList.size()) {
            arrWorkListKey.set(position, workList.getKey());
            arrWorkList.set(position, workList);
            notifyItemChanged(position);
        }

    }
}
