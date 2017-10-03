package com.example.viet.workup.ui.board.card;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viet.workup.R;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.utils.ApplicationUtils;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

/**
 * Created by viet on 13/09/2017.
 */

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "MemberRecyclerViewAdapter";
    private ArrayList<UserInfo> mArrUserInfo;
    private Context mContext;

    public MemberRecyclerViewAdapter(ArrayList<UserInfo> arrUserInfo) {
        this.mArrUserInfo = arrUserInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_card_recycler_view, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MemberViewHolder memberViewHolder = (MemberViewHolder) holder;
        UserInfo userInfo = mArrUserInfo.get(position);
        memberViewHolder.setData(userInfo);
    }

    @Override
    public int getItemCount() {
        return mArrUserInfo.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivMember)
        CircleImageView ivMember;

        public MemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewCompat.setNestedScrollingEnabled(itemView, false);
        }

        public void setData(final UserInfo userInfo) {
            FireBaseDatabaseUtils.userAccountRef(userInfo.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo uf = dataSnapshot.getValue(UserInfo.class);
                    ApplicationUtils.picasso(mContext, uf.getPhotoUrl(), ivMember);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


//            getDrawableObservable(userInfo.getPhotoUrl())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Drawable>() {
//                        @Override
//                        public void accept(Drawable drawable) throws Exception {
//                            ivMember.setImageDrawable(drawable);
//                        }
//                    });
        }
    }

    public void addItem(UserInfo userInfo) {
        mArrUserInfo.add(userInfo);
        notifyItemInserted(mArrUserInfo.size() - 1);
    }

    public void removeItem(int position) {
        if (position < mArrUserInfo.size()) {
            mArrUserInfo.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Observable<Drawable> getDrawableObservable(final String url) {
        return Observable.fromCallable(new Callable<Drawable>() {
            @Override
            public Drawable call() throws Exception {
                URL url1 = new URL(url);
                URLConnection connection = url1.openConnection();
                InputStream inputStream = connection.getInputStream();
                Drawable bitmapDrawable = BitmapDrawable.createFromStream(inputStream, "photo");
                return bitmapDrawable;
            }
        });
    }
}
