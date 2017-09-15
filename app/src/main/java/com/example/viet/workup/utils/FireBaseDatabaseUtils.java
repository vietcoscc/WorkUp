package com.example.viet.workup.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by viet on 06/09/2017.
 */

public class FireBaseDatabaseUtils {
    public static final String ACCOUNT = "Account";
    public static final String BOARD = "Board";
    public static final String CARD_LIST = "CardList";
    public static final String BOARD_DATA = "BoardData";
    public static final String STAR_BOARD = "StarBoard";
    public static final String UNSTAR_BOARD = "UnstarBoard";
    public static final String ARR_CARD = "arrCard";
    public static final String ARR_LABEL = "arrLabel";
    public static final String CARD_KEY = "CardKey";
    public static final String CARD_DATA = "CardData";
    public static final String CARD = "card";
    private static final String ARR_USER_INFO = "arrUserInfo";
    private static final String ARR_WORK_LIST = "arrWorkList";
    private static final String ARR_COMMENT_LIST = "arrComment";
    private static final String DUE_DATE = "dueDate";

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

    public static DatabaseReference boardDataRef(String boardKey) {
        return mRootRef.child(BOARD_DATA).child(boardKey);
    }

    public static DatabaseReference cardListRef(String boardKey, String cardListKey) {
        return boardDataRef(boardKey).child(cardListKey);
    }

    public static DatabaseReference arrCardRef(String boardKey, String cardListKey) {
        return cardListRef(boardKey, cardListKey).child(ARR_CARD);
    }

    public static DatabaseReference cardDataRef(String cardKey) {
        return mRootRef.child(CARD_DATA).child(cardKey);
    }

    public static DatabaseReference labelCardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(ARR_LABEL);
    }

    public static DatabaseReference dueDateCardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(DUE_DATE);
    }

    public static DatabaseReference memberCardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(ARR_USER_INFO);
    }

    public static DatabaseReference arrWorkListRef(String cardKey) {
        return cardDataRef(cardKey).child(ARR_WORK_LIST);
    }

    public static DatabaseReference arrCommentListRef(String cardKey) {
        return cardDataRef(cardKey).child(ARR_COMMENT_LIST);
    }

}
