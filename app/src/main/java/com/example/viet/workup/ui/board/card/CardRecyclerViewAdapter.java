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

import com.example.viet.workup.R;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.model.DueDate;
import com.example.viet.workup.model.Label;
import com.example.viet.workup.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 12/09/2017.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Card> mArrCard;
    private Context mContext;
    private Listener.OnItemClickListenter mOnItemClickListenter;
    private int lastPosition = -1;

    public CardRecyclerViewAdapter(ArrayList<Card> arrCard) {
        this.mArrCard = arrCard;
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
        cardViewHolder.setData(card);

        if (position > lastPosition) {
            AlphaAnimation animation = (AlphaAnimation) AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mArrCard.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
//        @BindView(R.id.tvAttachemt)
//        TextView tvAttachment;
        @BindView(R.id.tvCheck)
        TextView tvCheck;
        @BindView(R.id.tvDueDate)
        TextView tvDueDate;
        @BindView(R.id.recyclerViewMember)
        RecyclerView recyclerViewMember;
        LabelRecyclerViewAdapter labelRecyclerViewAdapter;
        MemberRecyclerViewAdapter memberRecyclerViewAdapter;
        ArrayList<Label> arrLabel = new ArrayList<>();
        ArrayList<UserInfo> arrUserInfo = new ArrayList<>();

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            recyclerViewLabel.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            labelRecyclerViewAdapter = new LabelRecyclerViewAdapter(arrLabel);
            recyclerViewLabel.setAdapter(labelRecyclerViewAdapter);

            recyclerViewMember.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
            memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(arrUserInfo);
            recyclerViewMember.setAdapter(memberRecyclerViewAdapter);

        }

        void setData(Card card) {
            String coverImageUrl = card.getCoverImageUrl();
            String title = card.getTitle();
            int commentCount = card.getCommentCount();
            int attachment = card.getAttachment();
            String checkWork = card.getCheckWork();
            DueDate dueDate = card.getDueDate();
            ArrayList<Label> labels = card.getArrLabel();
            ArrayList<UserInfo> userInfos = card.getArrUserInfo();
            boolean hasDescription = card.isHasDescription();
            tvTitle.setText(title);
            if (coverImageUrl == null || coverImageUrl.isEmpty()) {
                ivCover.setVisibility(View.GONE);
            } else {
                ivCover.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(coverImageUrl)
                        .resize(500,300)
                        .centerCrop()
                        .placeholder(android.R.drawable.screen_background_light)
                        .error(android.R.drawable.screen_background_dark)
                        .into(ivCover);
            }
//            if (attachment == 0) {
//                ivAttachment.setVisibility(View.GONE);
//                tvAttachment.setVisibility(View.GONE);
//            } else {
//                tvAttachment.setText(attachment + "");
//            }
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
            if (labels == null || labels.isEmpty()) {
                recyclerViewLabel.setVisibility(View.GONE);
            } else {
                arrLabel.clear();
                arrLabel.addAll(labels);
                labelRecyclerViewAdapter.notifyDataSetChanged();
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
    }

    public void addItem(Card card) {
        mArrCard.add(card);
        notifyItemInserted(mArrCard.size() - 1);
    }

    public void setOnItemClickListenter(Listener.OnItemClickListenter onItemClickListenter) {
        this.mOnItemClickListenter = onItemClickListenter;
    }
}
