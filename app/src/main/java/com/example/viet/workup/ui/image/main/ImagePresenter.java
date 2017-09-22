package com.example.viet.workup.ui.image.main;

import android.app.ProgressDialog;

import com.example.viet.workup.api.WallpaperApi;
import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.image.CategoryList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by viet on 29/08/2017.
 */

public class ImagePresenter<V extends ImageMvpView> extends BasePresenter<V> implements ImageMvpPresenter<V> {
    private static final String TAG = "ImagePresenter";
    private Retrofit mRetrofit;

    @Inject
    public ImagePresenter(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }

    @Override
    public void getCategory() {
        getmMvpView().showProgress();
        Call<CategoryList> call = mRetrofit.create(WallpaperApi.class).getCategory();
        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                CategoryList categoryList = response.body();
                getmMvpView().hideProgress();
                getmMvpView().displayCategory(categoryList.getArrCategory());
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                getmMvpView().hideProgress();
            }
        });
    }


}
