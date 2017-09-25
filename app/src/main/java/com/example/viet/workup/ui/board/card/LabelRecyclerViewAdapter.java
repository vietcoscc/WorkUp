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
import com.example.viet.workup.model.Label;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 13/09/2017.
 */

public class LabelRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Label> arrLabel;
    private Context mContext;

    public LabelRecyclerViewAdapter(ArrayList<Label> arrLabel) {
        this.arrLabel = arrLabel;
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
        labelViewHolder.setData(arrLabel.get(position));
    }

    @Override
    public int getItemCount() {
        return arrLabel.size();
    }

    class LabelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvLabel)
        TextView tvLabel;

        public LabelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewCompat.setNestedScrollingEnabled(itemView, false);
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
    }

    public void addItem(Label label) {
        arrLabel.add(label);
        notifyItemInserted(arrLabel.size() - 1);
    }

    public void removeItem(int position) {
        if (position < arrLabel.size()) {
            arrLabel.remove(position);
            notifyItemRemoved(position);
        }
    }
}
