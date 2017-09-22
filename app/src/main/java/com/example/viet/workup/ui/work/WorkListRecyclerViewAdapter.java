package com.example.viet.workup.ui.work;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.model.Task;
import com.example.viet.workup.model.WorkList;
import com.example.viet.workup.utils.ApplicationUtils;
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
    private Context mContext;
    private String mCardKey;

    public WorkListRecyclerViewAdapter(ArrayList<WorkList> arrWorkList, String cardKey) {
        this.arrWorkList = arrWorkList;
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
        viewHolder.setdata(arrWorkList.get(position).getTitle(), position + "");
    }


    @Override
    public int getItemCount() {
        return arrWorkList.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvHeader)
        TextView tvHeader;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;
        @BindView(R.id.recyclerViewTask)
        RecyclerView recyclerViewTask;
        TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;
        ArrayList<Task> arrTask = new ArrayList<>();

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivDelete.setOnClickListener(this);
            recyclerViewTask.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            ViewCompat.setNestedScrollingEnabled(recyclerViewTask, false);
        }

        public void setdata(String title, final String workListPosition) {
            taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(arrTask, mCardKey, workListPosition);
            recyclerViewTask.setAdapter(taskListRecyclerViewAdapter);

            arrTaskListRef(mCardKey, workListPosition).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.i(TAG, "onChildAdded");
                    Task task = dataSnapshot.getValue(Task.class);
                    taskListRecyclerViewAdapter.addItem(task);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.i(TAG, "onChildChanged");
                    Task task = dataSnapshot.getValue(Task.class);
                    int position = Integer.valueOf(dataSnapshot.getKey());
                    taskListRecyclerViewAdapter.changeItem(task, position);
                }

                @Override
                public void onChildRemoved(final DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            arrTaskListRef(mCardKey, workListPosition).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    taskListRecyclerViewAdapter.sortItem();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            tvHeader.setText(title);
            taskListRecyclerViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.ivDelete) {
                Dialog dialog = ApplicationUtils.buildConfirmDialog(mContext, "Are you sure want to delete ?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrWorkListRef(mCardKey).child(getPosition() + "").removeValue();
                            }
                        });
                dialog.show();
            }
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
