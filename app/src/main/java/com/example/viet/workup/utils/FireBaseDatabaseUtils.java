package com.example.viet.workup.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by viet on 06/09/2017.
 */

public class FireBaseDatabaseUtils {
    public static final String ACCOUNT = "Account";
    public static final String BOARD = "Board";
    public static final String STAR_BOARD = "StarBoard";
    public static final String UNSTAR_BOARD = "UnstarBoard";
    private static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    public static DatabaseReference accountRef() {
        return mRootRef.child(ACCOUNT);
    }

    public static DatabaseReference userAccountRef(String uid) {
        return accountRef().child(uid);
    }

    public static DatabaseReference boardRef() {
        return mRootRef.child(BOARD);
    }


    public static DatabaseReference starBoardRef(String uid) {
        return boardRef().child(STAR_BOARD).child(uid);
    }

    public static DatabaseReference unstarBoardRef(String uid) {
        return boardRef().child(UNSTAR_BOARD).child(uid);
    }
}
