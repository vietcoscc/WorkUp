package com.example.viet.workup.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by viet on 06/09/2017.
 */

public class FireBaseDatabaseUtils {
    public static final String UID = "uid";
    public static final String ACCOUNT = "account";
    public static final String BOARD = "board";
    public static final String CARD_LIST = "cardList";
    public static final String BOARD_DATA = "boardData";
    public static final String STAR_BOARD = "starBoard";
    public static final String UNSTAR_BOARD = "unstarBoard";
    public static final String ARR_CARD = "arrCard";
    public static final String ARR_LABEL = "arrLabel";
    public static final String CARD_KEY = "cardKey";
    public static final String CARD_DATA = "cardData";
    public static final String CARD = "card";
    private static final String ARR_USER_INFO = "arrUserInfo";
    private static final String ARR_WORK_LIST = "arrWorkList";
    private static final String ARR_COMMENT_LIST = "arrComment";
    private static final String DUE_DATE = "dueDate";
    private static final String MEMBER = "member";
    private static final String ARR_TASK_REF = "arrTask";
    private static final String HAS_DONE = "hasDone";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String COMMENT_COUNT = "commentCount";
    private static final String COVER_IMAGE = "coverImageUrl";
    private static final String IMAGE_URL = "imageUrl";
    private static final String IS_STAR = "star";
    private static final String OTHER_BOARD = "otherBoard";
    public static final String ARR_ACTIVITY = "arrActivity";
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

    public static DatabaseReference otherBoardRef(String uid) {
        return boardRef().child(OTHER_BOARD).child(uid);
    }

    public static DatabaseReference boardDataRef(String boardKey) {
        return mRootRef.child(BOARD_DATA).child(boardKey);
    }

    public static DatabaseReference boardImageUrlRef(boolean isStar, String uid, String boardKey) {
        if (isStar) {
            return starBoardRef(uid).child(boardKey).child(IMAGE_URL);
        } else {
            return unstarBoardRef(uid).child(boardKey).child(IMAGE_URL);
        }
    }

    public static DatabaseReference boardIsStarRef(boolean isStar, String uid, String boardKey) {
        if (isStar) {
            return starBoardRef(uid).child(boardKey).child(IS_STAR);
        } else {
            return unstarBoardRef(uid).child(boardKey).child(IS_STAR);
        }
    }

    public static DatabaseReference memberBoardRef(String boardKey) {
        return mRootRef.child(BOARD_DATA).child(boardKey).child(MEMBER);
    }

    public static DatabaseReference cardListRef(String boardKey, String cardListKey) {
        return boardDataRef(boardKey).child(cardListKey);
    }

    public static DatabaseReference arrCardRef(String boardKey, String cardListKey) {
        return boardDataRef(boardKey).child(ARR_CARD).child(cardListKey);
    }

    public static DatabaseReference cardDataRef(String cardKey) {
        return mRootRef.child(CARD_DATA).child(cardKey);
    }

    public static DatabaseReference cardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]);
    }

    public static DatabaseReference labelCardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(ARR_LABEL);
    }

    public static DatabaseReference coverImageCardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(COVER_IMAGE);
    }

    public static DatabaseReference titleCardRed(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(TITLE);
    }

    public static DatabaseReference commentCountRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(COMMENT_COUNT);
    }

    public static DatabaseReference descriptionCardRef(String cardKey) {
        return cardDataRef(cardKey).child(DESCRIPTION);
    }

    public static DatabaseReference dueDateCardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(DUE_DATE);
    }

    public static DatabaseReference memberCardRef(String cardKey) {
        return arrCardRef(cardKey.split("\\+")[0], cardKey.split("\\+")[1]).child(cardKey.split("\\+")[2]).child(ARR_USER_INFO);
    }

    public static DatabaseReference arrActivityRef(String boardKey) {
        return boardDataRef(boardKey).child(ARR_ACTIVITY);
    }

    public static DatabaseReference arrWorkListRef(String cardKey) {
        return cardDataRef(cardKey).child(ARR_WORK_LIST);
    }

    public static DatabaseReference arrTaskListRef(String cardKey, String workListKey) {
        return arrWorkListRef(cardKey).child(ARR_TASK_REF).child(workListKey);
    }

    public static DatabaseReference taskRef(String cardKey, String workListPosition, String taskPosiTion) {
        return arrWorkListRef(cardKey).child(ARR_TASK_REF).child(workListPosition).child(taskPosiTion);
    }

    public static DatabaseReference taskHasDoneRef(String cardKey, String workListPosition, String taskPosiTion) {
        return taskRef(cardKey, workListPosition, taskPosiTion).child(HAS_DONE);
    }

    public static DatabaseReference arrCommentListRef(String cardKey) {
        return cardDataRef(cardKey).child(ARR_COMMENT_LIST);
    }

}
