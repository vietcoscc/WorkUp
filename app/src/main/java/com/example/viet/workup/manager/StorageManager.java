package com.example.viet.workup.manager;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by viet on 19/09/2017.
 */

public class StorageManager {
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private static StorageManager mInstance;

    public static StorageManager newInstance() {
        if (mInstance == null) {
            mInstance = new StorageManager();
            return mInstance;
        } else {
            return mInstance;
        }
    }

    public void uploadImage(byte[] b) {

    }
}
