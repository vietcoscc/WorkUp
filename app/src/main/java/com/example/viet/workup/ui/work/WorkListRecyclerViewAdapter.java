package com.example.viet.workup.ui.work;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.model.Task;
import com.example.viet.workup.model.WorkList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 13/09/2017.
 */

public class WorkListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    private static final String TAG = "WorkListRecyclerViewAdapter";

    private ArrayList<WorkList> arrWorkList;
    private Context mContext;

    public WorkListRecyclerViewAdapter(ArrayList<WorkList> arrWorkList) {
        this.arrWorkList = arrWorkList;
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
        ArrayList<Task> arrTask = arrWorkList.get(position).getArrTask();
        if (arrTask == null) {
            arrTask = new ArrayList<>();
        }
        viewHolder.setdata(arrWorkList.get(position).getTitle(), arrTask);
    }


    @Override
    public int getItemCount() {
        return arrWorkList.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvHeader)
        TextView tvHeader;
        @BindView(R.id.recyclerViewTask)
        RecyclerView recyclerViewTask;
        TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;
        ArrayList<Task> arrTask = new ArrayList<>();

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recyclerViewTask.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            ViewCompat.setNestedScrollingEnabled(recyclerViewTask, false);
            taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(arrTask);
            recyclerViewTask.setAdapter(taskListRecyclerViewAdapter);
        }

        public void setdata(String title, ArrayList<Task> tasks) {
            tvHeader.setText(title);
            arrTask.clear();
            arrTask.addAll(tasks);
            taskListRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


    public void addItem(WorkList workList) {
        arrWorkList.add(workList);
        notifyItemInserted(arrWorkList.size() - 1);
    }

    public void removeItem(int postition) {
        arrWorkList.remove(postition);
        notifyItemRemoved(postition);
    }
}
