package com.example.viet.workup.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.viet.workup.R;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.model.Board;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 06/09/2017.
 */

public class MyboardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Board> mArrBoard;
    private Context context;
    private boolean type;
    private Listener.OnItemClickListenter mOnItemClickListenter;
    private Listener.OnItemLongClickListener mOnItemLongClickListener;

    public MyboardRecyclerViewAdapter(ArrayList<Board> arrBoard, boolean type) {
        this.mArrBoard = arrBoard;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if (type) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_board_recycler_view, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_star_board_recycler_view, parent, false);
        }
        return new MyboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyboardViewHolder viewHolder = (MyboardViewHolder) holder;
        viewHolder.setData(mArrBoard.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrBoard.size();
    }

    class MyboardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ivBackground)
        ImageView ivBackground;
        @BindView(R.id.ivStar)
        ImageView ivStar;
        public MyboardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListenter != null) {
                        mOnItemClickListenter.onClick(view, getPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onLongClick(view, getPosition());
                    }
                    return false;
                }
            });
        }

        void setData(Board board) {
            tvTitle.setText(board.getTitle());
            if (board.getImageUrl() == null || board.getImageUrl().isEmpty()) {
                ivBackground.setBackgroundColor(Color.BLUE);
            } else {
                Glide.with(context).load(board.getImageUrl()).into(ivBackground);
            }
            if (board.isStar()) {
                ivStar.setVisibility(View.VISIBLE);
            } else {
                ivStar.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setmOnItemClickListenter(Listener.OnItemClickListenter mOnItemClickListenter) {
        this.mOnItemClickListenter = mOnItemClickListenter;
    }

    public void setmOnItemLongClickListener(Listener.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}
