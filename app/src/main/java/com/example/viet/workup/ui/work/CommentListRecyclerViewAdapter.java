package com.example.viet.workup.ui.work;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by viet on 14/09/2017.
 */

public class CommentListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Comment> arrComment;
    private Context mContext;

    public CommentListRecyclerViewAdapter(ArrayList<Comment> arrComment) {
        this.arrComment = arrComment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment_recycler_view, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        commentViewHolder.setData(arrComment.get(position));
    }

    @Override
    public int getItemCount() {
        return arrComment.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tvDisplayName)
        TextView tvDisplayName;
        @BindView(R.id.tvComment)
        TextView tvComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Comment comment) {
            if (comment.getUserInfo().getPhotoUrl().isEmpty()) {
                Picasso.with(mContext).load(R.mipmap.ic_launcher_round)
                        .placeholder(android.R.drawable.screen_background_light)
                        .error(android.R.drawable.screen_background_dark)
                        .into(ivAvatar);
            } else {
                Picasso.with(mContext).load(comment.getUserInfo().getPhotoUrl())
                        .placeholder(android.R.drawable.screen_background_light)
                        .error(android.R.drawable.screen_background_dark)
                        .into(ivAvatar);
            }

            tvDisplayName.setText(comment.getUserInfo().getDisplayName());
            tvComment.setText(comment.getContent());
        }
    }

    public void addItem(Comment comment) {
        arrComment.add(comment);
        notifyItemInserted(arrComment.size() - 1);
    }

    public void removeItem(int position) {
        arrComment.remove(position);
        notifyItemRemoved(position);
    }
}
