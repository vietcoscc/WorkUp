package com.example.viet.workup.ui.board;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.model.BoardUserActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 24/09/2017.
 */

public class BoardActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<BoardUserActivity> mArrBoardActivity;

    public BoardActivityAdapter(ArrayList<BoardUserActivity> arrBoardActivity) {
        this.mArrBoardActivity = arrBoardActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_activity_recycler_view, parent, false);
        return new BoardAcvitiyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BoardAcvitiyViewHolder viewHolder = (BoardAcvitiyViewHolder) holder;
        viewHolder.setData(mArrBoardActivity.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrBoardActivity.size();
    }

    class BoardAcvitiyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvMessage)
        TextView tvMessage;
        @BindView(R.id.tvTimeStamp)
        TextView tvTimeStamp;

        public BoardAcvitiyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(BoardUserActivity boardActivity) {
            String from = boardActivity.getFrom();
            String message = boardActivity.getMessage();
            String target = boardActivity.getTarget();
            SpannableString spannableString = new SpannableString(from + " " + message + " " + target);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    from.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new UnderlineSpan(), 0,
                    from.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), from.length() + message.length(),
                    spannableString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new UnderlineSpan(), from.length() + message.length(),
                    spannableString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMessage.setText(spannableString);
            tvTimeStamp.setText(boardActivity.getTimeStamp());
        }
    }

    public void addItem(BoardUserActivity boardUserActivity) {
        mArrBoardActivity.add(0, boardUserActivity);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
        if (-1 < position && position < mArrBoardActivity.size()) {
            mArrBoardActivity.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clearItem() {
        mArrBoardActivity.clear();
        notifyDataSetChanged();
    }
}
