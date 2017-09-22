package com.example.viet.workup.ui.work;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.arrTaskListRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.taskHasDoneRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.taskRef;

/**
 * Created by viet on 13/09/2017.
 */

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_TASK = 1;
    public static final int TYPE_ADD_TASK = 2;

    private ArrayList<Task> arrTask;
    private Context mContext;
    private String mCardKey;
    private String mWorkPosition;

    public TaskListRecyclerViewAdapter(ArrayList<Task> arrTask, String cardKey, String workPosition) {
        this.arrTask = arrTask;
        this.mCardKey = cardKey;
        this.mWorkPosition = workPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == TYPE_TASK) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_work_list_recycler_view, parent, false);
            return new TaskViewHolder(view);
        } else {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_task_reycler_view, parent, false);
            return new AddTaskViewHolder(view1);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TaskViewHolder) {
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
            taskViewHolder.setData(arrTask.get(position));
        }
        if (holder instanceof AddTaskViewHolder) {
            AddTaskViewHolder addTaskViewHolder = (AddTaskViewHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return arrTask.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < arrTask.size()) {
            return TYPE_TASK;
        } else {
            return TYPE_ADD_TASK;
        }
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @BindView(R.id.tvTask)
        TextView tvTask;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewCompat.setNestedScrollingEnabled(itemView, false);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {

            taskHasDoneRef(mCardKey, mWorkPosition, getPosition() + "").setValue(!checkBox.isChecked());
        }

        public void setData(Task task) {
            tvTask.setText(task.getTask());
            checkBox.setChecked(task.isHasDone());
            if (checkBox.isChecked()) {
                tvTask.setPaintFlags(tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tvTask.setPaintFlags(0);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Are you sure want to delete");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    taskRef(mCardKey, mWorkPosition, getPosition() + "").setValue(null);
                }
            });
            builder.setNegativeButton("CAncel", null);
            builder.create().show();
            return false;
        }
    }

    class AddTaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.edtAddTask)
        EditText edtAddTask;
        @BindView(R.id.ivAddTask)
        ImageView ivAddTask;
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    ivAddTask.setVisibility(View.GONE);
                } else {
                    ivAddTask.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                Task task = new Task(edtAddTask.getText() + "", false);
                taskRef(mCardKey, mWorkPosition, count + "").setValue(task);
                edtAddTask.setText("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        public AddTaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewCompat.setNestedScrollingEnabled(itemView, false);
            edtAddTask.addTextChangedListener(textWatcher);
            ivAddTask.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(edtAddTask.getWindowToken(), 0);
            arrTaskListRef(mCardKey, mWorkPosition).addListenerForSingleValueEvent(valueEventListener);
        }
    }

    public void addItem(Task task) {
        arrTask.add(task);
        notifyItemInserted(arrTask.size() - 1);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < arrTask.size()) {
            arrTask.remove(position);
            notifyItemRemoved(position);
        }

    }

    public void sortItem() {
        Collections.sort(arrTask, comparator);
        notifyDataSetChanged();
    }

    public void changeItem(Task task, int position) {
        arrTask.get(position).setTask(task.getTask());
        arrTask.get(position).setHasDone(task.isHasDone());
        notifyItemChanged(position);
    }

    public void clearItem() {
        arrTask.clear();
        notifyDataSetChanged();
    }

    private Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task task, Task t1) {
            Toast.makeText(mContext, "Compare", Toast.LENGTH_SHORT).show();
            if (task.isHasDone() == true && t1.isHasDone() == false) {
                return 1;
            } else if (task.isHasDone() == false && t1.isHasDone() == true) {
                return -1;
            } else {
                return 0;
            }
        }
    };
}
