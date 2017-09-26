package com.example.viet.workup.ui.image.item;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.model.image.Image;
import com.example.viet.workup.ui.image.item.menu.ItemImageImageOptionMenu;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.common.Constants.CATEGORY_ID;
import static com.example.viet.workup.common.Constants.IMAGE_SPAN_COUNT;

public class ItemActivity extends BaseActivity implements ItemMvpView {
    @Inject
    ItemPresenter<ItemMvpView> mItemPresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mCId;
    private ItemAdapter mAdapter;
    private ArrayList<Image> mArrImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);
        getExtras();
        initPresenter();
        initViews();
        initProgress();
        if (!mCId.equals("0")) {
            mItemPresenter.getCategoryItem( mCId);
        } else {
            mItemPresenter.getLastest();
        }
    }

    private void initViews() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, IMAGE_SPAN_COUNT));
        mAdapter = new ItemAdapter(mArrImage);
        mAdapter.setmOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mItemPresenter.createImageViewer(ItemActivity.this, view, position, mArrImage);
            }
        });
        mAdapter.setmOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                ItemImageImageOptionMenu optionMenu = new ItemImageImageOptionMenu(ItemActivity.this, view, mArrImage, position);
                optionMenu.show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void initPresenter() {
//        MyApplication application = (MyApplication) getApplication();
//        application.getActivityComponent().inject(this);
        getmActivityComponent().inject(this);
        mItemPresenter.onAttach(this);
    }

    private void getExtras() {
        mCId = getIntent().getExtras().getString(CATEGORY_ID);
    }

    private void initProgress() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void displayImage(ArrayList<Image> arrImage) {
        mArrImage.addAll(arrImage);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayImageViewer(ImageViewer imageViewer) {
        imageViewer.show();
    }

    @Override
    public void displayPopupMenu(PopupMenu popupMenu) {
        popupMenu.show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mItemPresenter.onAttach(this);
    }
}
