package com.example.viet.workup.ui.image.item.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.model.image.Image;
import com.example.viet.workup.utils.FireBaseDatabaseUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.viet.workup.utils.FireBaseStorageUtils.getCurrentCardKey;
import static com.example.viet.workup.utils.FireBaseStorageUtils.getImageCoverRef;

/**
 * Created by viet on 20/09/2017.
 */

public class ItemImageOptionPrsenter<V extends ItemImageOptionMvpView> extends BasePresenter<V> implements ItemImageOptionMvpPresenter<V> {
    @Inject
    public ItemImageOptionPrsenter() {
    }

    @Override
    public void onOpenImage() {

    }

    @Override
    public void onSaveImage(Image image) {

        String path = Environment.getExternalStorageDirectory().getPath()
                + "/" + Environment.DIRECTORY_DCIM + "/" + image.getCatId() + image.getId() + ".png";
        getDownloadObservable(image.getWallpaperImage(), path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isSuccessful) throws Exception {
                        if (isSuccessful) {
                            getmMvpView().showMessge("Saved");
                        } else {
                            getmMvpView().showMessge("Failed");
                        }
                    }
                });
    }


    @Override
    public void onSetImage(Image image) {
        getImageBitmapObservable(image.getWallpaperImage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) {

                        setImage(getScaledBitmap(bitmap, 0.4f));
                    }
                });
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getScaledBitmap(Bitmap bitmap, float rate) {
        int w = (int) (bitmap.getWidth() * rate);
        int h = (int) (bitmap.getHeight() * rate);
        return Bitmap.createScaledBitmap(bitmap, w, h, false);
    }

    private void setImage(Bitmap bitmap) {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte b[] = bos.toByteArray();
            UploadTask uploadTask = getImageCoverRef(getCurrentCardKey()).putBytes(b);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    FireBaseDatabaseUtils.coverImageCardRef(getCurrentCardKey()).setValue(taskSnapshot.getDownloadUrl().toString());
                    if(getmMvpView()==null){
                        return;
                    }
                    getmMvpView().showMessge("onSuccess");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(getmMvpView()==null){
                        return;
                    }
                    getmMvpView().showMessge("onFailure");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Observable<Boolean> getDownloadObservable(final String urlImage, final String path) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                try {
                    URL url = new URL(urlImage);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    File file = new File(path);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream outputStream = new FileOutputStream(file);
                    byte b[] = new byte[1024];
                    int count = inputStream.read(b);
                    while (count != -1) {
                        outputStream.write(b, 0, count);
                        count = inputStream.read(b);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });
    }

    private Observable<Bitmap> getImageBitmapObservable(final String urlImage) {
        return Observable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                URL url = new URL(urlImage);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(inputStream);
                return bitMap;
            }
        });
    }
}
