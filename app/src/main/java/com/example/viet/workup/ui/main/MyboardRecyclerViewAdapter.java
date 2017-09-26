package com.example.viet.workup.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.model.Board;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 06/09/2017.
 */

public class MyboardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Board> mArrBoard;
    private ArrayList<String> marrBoardKey;
    private Context mContext;
    private boolean mType;
    private Listener.OnItemClickListenter mOnItemClickListenter;
    private Listener.OnItemLongClickListener mOnItemLongClickListener;
    Field field[] = R.raw.class.getFields();

    public MyboardRecyclerViewAdapter(ArrayList<Board> arrBoard, ArrayList<String> arrBoardKey, boolean type) {
        this.mArrBoard = arrBoard;
        this.marrBoardKey = arrBoardKey;
        this.mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view;
        if (mType) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_my_board_recycler_view, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_star_board_recycler_view, parent, false);
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
            board.getImageUrl();

            try {
//                InputStream inputStream = mContext.getResources().openRawResource();
                Picasso.with(mContext).load(field[board.getImageUrl()].getInt(null))
                        .resize(150, 100)
                        .centerCrop()
                        .placeholder(android.R.drawable.screen_background_light)
                        .error(android.R.drawable.screen_background_dark)
                        .into(ivBackground);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ivBackground.setBackgroundColor(Color.BLUE);


            if (board.isStar()) {
                ivStar.setVisibility(View.VISIBLE);
            } else {
                ivStar.setVisibility(View.INVISIBLE);
            }
            AnimationSet animation = (AnimationSet) AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            itemView.startAnimation(animation);
        }
    }

    public void addItem(Board board) {
        marrBoardKey.add(board.getKey());
        mArrBoard.add(board);
        notifyItemInserted(mArrBoard.size() - 1);
    }

    public void removeItem(int position) {
        if (position > -1 && position < marrBoardKey.size()) {
            marrBoardKey.remove(position);
            mArrBoard.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setmOnItemClickListenter(Listener.OnItemClickListenter mOnItemClickListenter) {
        this.mOnItemClickListenter = mOnItemClickListenter;
    }

    public void setmOnItemLongClickListener(Listener.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}
