package com.example.viet.workup.ui.profile;

import android.support.annotation.NonNull;

import com.example.viet.workup.base.BasePresenter;
import com.example.viet.workup.manager.AccountManager;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.utils.FireBaseStorageUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

import javax.inject.Inject;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.displayNameRef;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.userAccountRef;

/**
 * Created by viet on 02/09/2017.
 */

public class ProfilePresenter<V extends ProfileMvpView> extends BasePresenter<V> implements ProfileMvpPresenter<V> {
    private AccountManager mAccountManager = AccountManager.getsInstance();

    @Inject
    public ProfilePresenter() {
    }

    @Override
    public void onReceiveUserInfo() {
        userAccountRef(mAccountManager.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    if (getmMvpView() != null) {
                        getmMvpView().showUserInfo(userInfo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChangeDisplayName(final String displayName) {
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest
                .Builder()
                .setDisplayName(displayName).build();
        mAccountManager.getmAuth().getCurrentUser().updateProfile(changeRequest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        displayNameRef(mAccountManager.getCurrentUser().getUid()).setValue(displayName);
                        if (getmMvpView() != null) {
                            getmMvpView().showMessge("onSuccess");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (getmMvpView() != null) {
                            getmMvpView().showMessge("onFailure");
                        }
                    }
                });
    }

    @Override
    public void onChangeAvatar(InputStream stream) {

        UploadTask uploadTask = FireBaseStorageUtils
                .getAvatarRef(mAccountManager.getCurrentUser().getUid())
                .putStream(stream);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(taskSnapshot.getDownloadUrl())
                        .build();
                mAccountManager.getmAuth().getCurrentUser()
                        .updateProfile(changeRequest)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                userAccountRef(mAccountManager.getCurrentUser().getUid())
                                        .child("photoUrl")
                                        .setValue(taskSnapshot.getDownloadUrl() + "");
                                if (getmMvpView() != null) {
                                    getmMvpView().showMessge("onSuccess");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (getmMvpView() != null) {
                            getmMvpView().showMessge("onFailure");
                        }
                    }
                });

    }
}
