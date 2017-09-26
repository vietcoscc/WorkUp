package com.example.viet.workup.ui.board.card;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.model.Label;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 13/09/2017.
 */

public class LabelRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Label> mArrLabel;
    private ArrayList<String> mArrLabelKey;
    private Context mContext;
    private Listener.OnItemLongClickListener mOnItemLongClickListener;

    public LabelRecyclerViewAdapter(ArrayList<Label> arrLabel, ArrayList<String> arrLabelKey) {
        this.mArrLabel = arrLabel;
        this.mArrLabelKey = arrLabelKey;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_card_recycler_view, parent, false);
        return new LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LabelViewHolder labelViewHolder = (LabelViewHolder) holder;
        labelViewHolder.setData(mArrLabel.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrLabel.size();
    }

    class LabelViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        @BindView(R.id.tvLabel)
        TextView tvLabel;

        public LabelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewCompat.setNestedScrollingEnabled(itemView, false);
            itemView.setOnLongClickListener(this);
        }

        public void setData(Label label) {
            if (label == null) {
                return;
            }
            if (TextUtils.isEmpty(label.getColor())) {
                tvLabel.setBackgroundColor(Color.WHITE);
            } else {
                int color = Color.parseColor(label.getColor());
                tvLabel.setBackgroundColor(color);
            }

            if (TextUtils.isEmpty(label.getText())) {
                tvLabel.setText(" ");
            } else {
                tvLabel.setText(label.getText());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onLongClick(view, getPosition());
            }
            return false;
        }
    }

    public void addItem(Label label) {
        mArrLabelKey.add(label.getKey());
        mArrLabel.add(label);
        notifyItemInserted(mArrLabel.size() - 1);
    }

    public void removeItem(int position) {
        if (position > -1 && position < mArrLabel.size()) {
            mArrLabelKey.remove(position);
            mArrLabel.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void setOnItemLongClickListener(Listener.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}
