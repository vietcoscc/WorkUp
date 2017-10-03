package com.example.viet.workup.ui.work;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.model.Comment;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.utils.ApplicationUtils;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by viet on 14/09/2017.
 */

public class CommentListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Comment> mArrComment;
    private Context mContext;
    private String mCardKey;

    public CommentListRecyclerViewAdapter(ArrayList<Comment> arrComment, String cardKey) {
        this.mArrComment = arrComment;
        this.mCardKey = cardKey;
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
        commentViewHolder.setData(mArrComment.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrComment.size();
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

        public void setData(final Comment comment) {
            FireBaseDatabaseUtils.userAccountRef(comment.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    ApplicationUtils.picasso(mContext,userInfo.getPhotoUrl(),ivAvatar);
                    tvDisplayName.setText(userInfo.getDisplayName());
                    tvComment.setText(comment.getContent());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    public void addItem(Comment comment) {
        mArrComment.add(comment);
        notifyItemInserted(mArrComment.size() - 1);
    }

    public void removeItem(int position) {
        if (position > -1 && position < mArrComment.size()) {
            mArrComment.remove(position);
            notifyItemRemoved(position);
        }
    }
}
