package com.example.viet.workup.ui.board.member;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by viet on 18/09/2017.
 */

public class CardMemberRecyclerViewAdapetr extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<UserInfo> arrUserInfo;
    private Context mContext;
    private ArrayList<Boolean> arrBit;
    private String mBoardKey;
    private boolean isBoardInitiator;

    public CardMemberRecyclerViewAdapetr(ArrayList<UserInfo> arrUserInfo, String boardKey, boolean isBoardInitiator) {
        this.arrUserInfo = arrUserInfo;
        this.mBoardKey = boardKey;
        this.isBoardInitiator = isBoardInitiator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_recycler_view, parent, false);

        return new CardMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardMemberViewHolder viewHolder = (CardMemberViewHolder) holder;
        viewHolder.setData(arrUserInfo.get(position), position);
    }

    @Override
    public int getItemCount() {
        return arrUserInfo.size();
    }

    class CardMemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivAvatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tvDisplayName)
        TextView tvDisplayName;
        @BindView(R.id.checkbox)
        CheckBox checkBox;

        public CardMemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            checkBox.setChecked(true);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        FireBaseDatabaseUtils.memberBoardRef(mBoardKey)
                                .child(arrUserInfo.get(getPosition()).getUid())
                                .setValue(arrUserInfo.get(getPosition()));
                    } else {
                        FireBaseDatabaseUtils.memberBoardRef(mBoardKey)
                                .child(arrUserInfo.get(getPosition()).getUid())
                                .setValue(null);
                    }

                }
            });
        }

        public void setData(UserInfo userInfo, int position) {

            if (!(userInfo.getPhotoUrl() == null) && !userInfo.getPhotoUrl().isEmpty()) {
                Picasso.with(mContext).load(userInfo.getPhotoUrl())
                        .placeholder(android.R.drawable.screen_background_light)
                        .error(android.R.drawable.screen_background_dark)
                        .into(ivAvatar);
            } else {
                Picasso.with(mContext).load(R.mipmap.ic_launcher_round)
                        .placeholder(android.R.drawable.screen_background_light)
                        .error(android.R.drawable.screen_background_dark)
                        .into(ivAvatar);
            }

            tvDisplayName.setText(userInfo.getDisplayName());
//            getCheckObservable(arrUserInfo.get(position).getUid())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Boolean>() {
//                        @Override
//                        public void accept(@NonNull Boolean aBoolean) throws Exception {
//                            checkBox.setChecked(aBoolean);
//                        }
//                    });

        }

        @Override
        public void onClick(View view) {
            if (isBoardInitiator) {
                checkBox.setChecked(!checkBox.isChecked());
                if (onItemClickListenter != null) {

                    onItemClickListenter.onClick(view, getPosition());

                }
            } else {
                Toast.makeText(mContext, "Uneditable!", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public Observable<Boolean> getCheckObservable(final String uid) {
//        Log.i("Log", uid);
//        return Observable.fromCallable(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Log.i("Log", arrUserInfoCard.size() + "");
//                for (int i = 0; i < arrUserInfoCard.size(); i++) {
//                    Log.i("Log2", arrUserInfoCard.get(i).getUid());
//                    if (uid.trim().equals(arrUserInfoCard.get(i).getUid().trim())) {
//                        Log.i("Log", i + "");
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//    }

    public void addItem(UserInfo userInfo) {
        arrUserInfo.add(userInfo);
        notifyItemInserted(arrUserInfo.size() - 1);
    }

    public void removeItem(int position) {
        arrUserInfo.remove(position);
        notifyItemRemoved(position);
    }

    private Listener.OnItemClickListenter onItemClickListenter;

    public void setOnItemClickListenter(Listener.OnItemClickListenter onItemClickListenter) {
        this.onItemClickListenter = onItemClickListenter;
    }
}
