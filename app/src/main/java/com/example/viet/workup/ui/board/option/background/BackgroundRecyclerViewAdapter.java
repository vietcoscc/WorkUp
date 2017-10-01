package com.example.viet.workup.ui.board.option.background;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.viet.workup.R;
import com.example.viet.workup.utils.ApplicationUtils;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 20/09/2017.
 */

public class BackgroundRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Field> mArrImage = ApplicationUtils.getArrId();
    private Context mContext;
    private int mCurrentPosition;
    private BackgroundViewHolder currentHolder;

    public BackgroundRecyclerViewAdapter(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_background_board_recycler_view, parent, false);
        return new BackgroundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BackgroundViewHolder backgroundViewHolder = (BackgroundViewHolder) holder;
        try {
            if (position == mCurrentPosition) {
                currentHolder = (BackgroundViewHolder) holder;
                backgroundViewHolder.layoutCheck.setVisibility(View.VISIBLE);
            } else {
                backgroundViewHolder.layoutCheck.setVisibility(View.GONE);
            }
            backgroundViewHolder.setData(mArrImage.get(position).getInt(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mArrImage.size();
    }

    class BackgroundViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.layoutCheck)
        RelativeLayout layoutCheck;

        public BackgroundViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(int id) {
            Glide.with(mContext).load(id)
                    .centerCrop()
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(ivImage);
        }

        @Override
        public void onClick(View view) {
            currentHolder.layoutCheck.setVisibility(View.GONE);
            layoutCheck.setVisibility(View.VISIBLE);
            mCurrentPosition = getPosition();
            currentHolder = this;
        }
    }

    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

}
