package com.example.viet.workup.ui.image.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viet.workup.R;
import com.example.viet.workup.model.image.Category;
import com.example.viet.workup.utils.ApplicationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viet on 29/08/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Category> mArrCategory;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public ImageAdapter(ArrayList<Category> mArrCategory) {
        this.mArrCategory = mArrCategory;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_recycler_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
        Category category = mArrCategory.get(position);
        viewHolder.setData(category);
    }

    @Override
    public int getItemCount() {
        return mArrCategory.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivCategory)
        ImageView ivCategory;
        @BindView(R.id.tvCategoryName)
        TextView tvCategoryName;
        @BindView(R.id.tvTotalWallpaper)
        TextView tvTotalWallpaper;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(Category category) {
            ApplicationUtils.glide(mContext, category.getCategoryImageThumb(), ivCategory);
            tvCategoryName.setText(category.getCategoryName());
            tvTotalWallpaper.setText(category.getTotalWallpager());
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(view, getPosition());
            }
        }
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
