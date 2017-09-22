package com.example.viet.workup.ui.image.item;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.viet.workup.R;
import com.example.viet.workup.api.WallpaperApi;
import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.image.Image;
import com.example.viet.workup.model.image.ImageList;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by viet on 30/08/2017.
 */

public class ItemPresenter<V extends ItemMvpView> extends BasePresenter<V> implements ItemMvpPresenter<V> {

    private static final String TAG = "ItemPresenter";
    private Retrofit mRetrofit;

    @Inject
    public ItemPresenter(Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    @Override
    public void getLastest() {
        getmMvpView().showProgress();
        Call<ImageList> call = mRetrofit.create(WallpaperApi.class).getLastest();
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if (getmMvpView() == null) {
                    return;
                }
                getmMvpView().hideProgress();
                ImageList imageList = response.body();
                getmMvpView().displayImage(imageList.getArrImage());
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                if (getmMvpView() == null) {
                    return;
                }
                getmMvpView().hideProgress();
            }
        });
    }

    @Override
    public void getCategoryItem(String catId) {
        if (TextUtils.isEmpty(catId)) {
            Log.e(TAG, "Empty!");
            return;
        }
        getmMvpView().showProgress();
        Call<ImageList> call = mRetrofit.create(WallpaperApi.class).getCategoryItem(catId);
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                getmMvpView().hideProgress();
                ImageList imageList = response.body();
                getmMvpView().displayImage(imageList.getArrImage());
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                getmMvpView().hideProgress();
            }
        });
    }

    @Override
    public void createImageViewer(final Context context, View view, int position, ArrayList<Image> arrImage) {
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setFailureImage(android.R.drawable.screen_background_light)
                .setProgressBarImage(android.R.drawable.screen_background_dark_transparent)
                .setPlaceholderImage(android.R.drawable.screen_background_dark);
        View v = LayoutInflater.from(context).inflate(R.layout.view_image_viewer_overlay, (ViewGroup) view.getParent(), false);
        ImageView ivBack = v.findViewById(R.id.ivBack);
        ImageViewer.Formatter<Image> formatter = new ImageViewer.Formatter<Image>() {
            @Override
            public String format(Image image) {
                return image.getWallpaperImage();
            }
        };
        final ImageViewer imageViewer = new ImageViewer.Builder(context, arrImage)
                .setFormatter(formatter)
                .hideStatusBar(false)
                .setStartPosition(position)
                .setCustomDraweeHierarchyBuilder(hierarchyBuilder)
                .setOverlayView(v)
                .build();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageViewer.onDismiss();
                    }
                }, 500);

            }
        });

        getmMvpView().displayImageViewer(imageViewer);
    }

}
