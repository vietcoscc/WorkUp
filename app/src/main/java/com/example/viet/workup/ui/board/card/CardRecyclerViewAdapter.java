package com.example.viet.workup.ui.board.card;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.viet.workup.R;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.labelCardRef;

/**
 * Created by viet on 12/09/2017.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Card> mArrCard;
    private ArrayList<String> mArrCardKey;
    private Context mContext;
    private Listener.OnItemClickListenter mOnItemClickListenter;
    private Listener.OnItemLongClickListener mOnItemLongClickListener;
    private int mLastPosition = -1;

    public CardRecyclerViewAdapter(ArrayList<Card> arrCard, ArrayList<String> arrCardKey) {
        arrCard.clear();
        this.mArrCard = arrCard;
        this.mArrCardKey = arrCardKey;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_recycler_view, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardViewHolder cardViewHolder = (CardViewHolder) holder;
        Card card = mArrCard.get(position);
        cardViewHolder.setData(card, position);

        if (position > mLastPosition) {
            AlphaAnimation animation = (AlphaAnimation) AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mArrCard.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.ivCover)
        ImageView ivCover;
        //        @BindView(R.id.ivAttachment)
//        ImageView ivAttachment;
        @BindView(R.id.ivComment)
        ImageView ivComment;
        @BindView(R.id.ivCheck)
        ImageView ivCheck;
        @BindView(R.id.ivDueDate)
        ImageView ivDueDate;
        @BindView(R.id.ivDescription)
        ImageView ivDescription;
        @BindView(R.id.recyclerViewLabel)
        RecyclerView recyclerViewLabel;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvComment)
        TextView tvComment;
        @BindView(R.id.tvCheck)
        TextView tvCheck;
        @BindView(R.id.tvDueDate)
        TextView tvDueDate;
        @BindView(R.id.recyclerViewMember)
        RecyclerView recyclerViewMember;
        LabelRecyclerViewAdapter labelRecyclerViewAdapter;
        MemberRecyclerViewAdapter memberRecyclerViewAdapter;
        ArrayList<Label> arrLabel = new ArrayList<>();
        ArrayList<String> arrLabelKey = new ArrayList<>();
        ArrayList<UserInfo> arrUserInfo = new ArrayList<>();

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            recyclerViewLabel.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            labelRecyclerViewAdapter = new LabelRecyclerViewAdapter(arrLabel, arrLabelKey);
            recyclerViewLabel.setAdapter(labelRecyclerViewAdapter);

            recyclerViewMember.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
            memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(arrUserInfo);
            recyclerViewMember.setAdapter(memberRecyclerViewAdapter);

        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Label label = dataSnapshot.getValue(Label.class);
                label.setKey(dataSnapshot.getKey());
                labelRecyclerViewAdapter.addItem(label);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                labelRecyclerViewAdapter.removeItem(arrLabelKey.indexOf(dataSnapshot.getKey()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        void setData(Card card, int position) {
            labelCardRef(mArrCard.get(position).getCardKey()).removeEventListener(childEventListener);
            labelCardRef(mArrCard.get(position).getCardKey()).addChildEventListener(childEventListener);
            String coverImageUrl = card.getCoverImageUrl();
            String title = card.getTitle();
            int commentCount = card.getCommentCount();
            String checkWork = card.getCheckWork();
            DueDate dueDate = card.getDueDate();
            ArrayList<UserInfo> userInfos = card.getArrUserInfo();
            boolean hasDescription = card.isHasDescription();
            tvTitle.setText(title);
            if (coverImageUrl == null || coverImageUrl.isEmpty()) {
                ivCover.setVisibility(View.GONE);
            } else {
                ivCover.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(coverImageUrl)
                        .centerCrop()
                        .placeholder(android.R.drawable.screen_background_light)
                        .error(android.R.drawable.screen_background_dark)
                        .into(ivCover);
            }

            if (commentCount == 0) {
                ivComment.setVisibility(View.GONE);
                tvComment.setVisibility(View.GONE);
            } else {
                tvComment.setText(commentCount + "");
            }
            if (checkWork == null || checkWork.isEmpty()) {
                ivCheck.setVisibility(View.GONE);
                tvCheck.setVisibility(View.GONE);
            } else {
                tvCheck.setText(checkWork);
            }
            if (dueDate == null) {
                tvDueDate.setVisibility(View.GONE);
                ivDueDate.setVisibility(View.GONE);
            } else {
                tvDueDate.setText(dueDate.getDay() + " thg " + dueDate.getMonth() + " nm " + dueDate.getYear());
            }

            if (userInfos == null || userInfos.isEmpty()) {
                recyclerViewMember.setVisibility(View.GONE);
            } else {
                arrUserInfo.clear();
                arrUserInfo.addAll(userInfos);
                memberRecyclerViewAdapter.notifyDataSetChanged();
            }
            if (!hasDescription) {
                ivDescription.setVisibility(View.GONE);
            } else {

            }

        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListenter != null) {
                mOnItemClickListenter.onClick(view, getPosition());
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

    public void addItem(Card card) {
        mArrCard.add(card);
        mArrCardKey.add(card.getKey());
        notifyItemInserted(mArrCard.size() - 1);
    }

    public void clearItem() {
        mArrCard.clear();
        mArrCardKey.clear();
        notifyDataSetChanged();
    }

    public void changeItem(Card card, int position) {
        if (position > -1 && position < mArrCard.size()) {
            mArrCard.set(position, card);
            mArrCardKey.set(position, card.getKey());
            notifyItemChanged(position);
        }
    }

    public void removeItem(int position) {
        if (position > -1 && position < mArrCard.size()) {
            mArrCard.remove(position);
            mArrCardKey.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setOnItemClickListenter(Listener.OnItemClickListenter onItemClickListenter) {
        this.mOnItemClickListenter = onItemClickListenter;
    }

    public Listener.OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setOnItemLongClickListener(Listener.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}
