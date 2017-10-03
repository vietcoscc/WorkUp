package com.example.viet.workup.utils;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by viet on 19/09/2017.
 */

public class FireBaseStorageUtils {
    private static final String IMAEGE = "imageCover";
    private static final String IMAGE_THUMB = "imageCoverThumb";
    public static final String AVATAR = "avatar";
    private static StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private static String currentCardKey;

    public static StorageReference getImageCoverRef(String cardKey) {
        return mStorage.child(IMAEGE + "/" + cardKey + ".jpg");
    }

    public static StorageReference getAvatarRef(String uid) {
        return mStorage.child(AVATAR + "/" + uid + ".jpg");
    }

    public static void setCurrentCardKey(String currentCardKey) {
        FireBaseStorageUtils.currentCardKey = currentCardKey;
    }

    public static String getCurrentCardKey() {
        return currentCardKey;
    }
}
