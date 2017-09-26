package com.example.viet.workup.ui.image.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.viet.workup.MyApplication;
import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.model.image.Category;
import com.example.viet.workup.ui.image.item.ItemActivity;
import com.example.viet.workup.utils.ApplicationUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.common.Constants.CATEGORY_ID;
import static com.example.viet.workup.common.Constants.CATEGORY_SPAN_COUNT;

public class ImageActivity extends BaseActivity implements ImageMvpView {
    @Inject
    ImagePresenter<ImageMvpView> mMainPresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageAdapter mAdapter;
    private ArrayList<Category> mArrCategory = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initPresenter();
        iniViews();
        mMainPresenter.getCategory();
    }

    private void iniViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, CATEGORY_SPAN_COUNT));
        mAdapter = new ImageAdapter(mArrCategory);
        mAdapter.setmOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (!ApplicationUtils.isNetworkConnectionAvailable(ImageActivity.this)) {
                    Toast.makeText(ImageActivity.this, "Network is not available !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ImageActivity.this, ItemActivity.class);
                intent.putExtra(CATEGORY_ID, mArrCategory.get(position).getcId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void initPresenter() {
//        MyApplication application = (MyApplication) getApplication();
//        application.getActivityComponent().inject(this);
        getmActivityComponent().inject(this);
        mMainPresenter.onAttach(this);
    }



    @Override
    protected void onPause() {
        super.onPause();
        mMainPresenter.onDetach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.onAttach(this);
    }

    @Override
    public void displayCategory(ArrayList<Category> arrCategory) {
        mArrCategory.addAll(arrCategory);
        Category category = new Category("0", "Lastest", "", "", "");
        mArrCategory.add(category);
        mAdapter.notifyDataSetChanged();
    }
}
