package com.example.viet.workup.ui.image.item.menu;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BasePopupMenu;
import com.example.viet.workup.model.image.Image;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by viet on 20/09/2017.
 */

public class ItemImageImageOptionMenu extends BasePopupMenu implements ItemImageOptionMvpView, MenuItem.OnMenuItemClickListener {
    @Inject
    ItemImageOptionPrsenter<ItemImageOptionMvpView> mPresenter;

    private ArrayList<Image> mArrImage;
    private int mPosition;

    public ItemImageImageOptionMenu(Context context, View anchor, ArrayList<Image> arrImage, int position) {
        super(context, anchor);
        this.mContext = context;
        this.mArrImage = arrImage;
        this.mPosition = position;
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        initViews();

    }

    private void initViews() {
        getMenuInflater().inflate(R.menu.menu_item_image_popup, getMenu());
        MenuItem open = getMenu().findItem(R.id.action_open);
        MenuItem save = getMenu().findItem(R.id.action_save);
        MenuItem setImage = getMenu().findItem(R.id.action_set_as_background);
        open.setOnMenuItemClickListener(this);
        save.setOnMenuItemClickListener(this);
        setImage.setOnMenuItemClickListener(this);
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            String write = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
            String read = android.Manifest.permission.READ_EXTERNAL_STORAGE;
            if (ContextCompat.checkSelfPermission(mContext, write) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(mContext, read) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        }
    }

    private void requestPermission() {
        Toast.makeText(mContext, "requestPermission", Toast.LENGTH_SHORT).show();
        String write = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String read = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{write, read}, 0);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_open) {
            mPresenter.onOpenImage();
        } else if (id == R.id.action_save) {
            if (hasPermission()) {
                mPresenter.onSaveImage(mArrImage.get(mPosition));
            } else {
                requestPermission();
            }
        } else if (id == R.id.action_set_as_background) {
            mPresenter.onSetImage(mArrImage.get(mPosition));
        }
        return false;
    }

}
