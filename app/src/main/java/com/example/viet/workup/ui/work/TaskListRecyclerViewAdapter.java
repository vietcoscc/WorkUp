package com.example.viet.workup.ui.work;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.model.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 13/09/2017.
 */

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_TASK = 1;
    public static final int TYPE_ADD_TASK = 2;

    private ArrayList<Task> arrTask;

    public TaskListRecyclerViewAdapter(ArrayList<Task> arrTask) {
        this.arrTask = arrTask;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    class TaskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @BindView(R.id.tvTask)
        TextView tvTask;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AddTaskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.edtAddTask)
        EditText edtAddTask;

        public AddTaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
