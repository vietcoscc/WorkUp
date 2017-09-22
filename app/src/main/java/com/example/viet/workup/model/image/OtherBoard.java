package com.example.viet.workup.model.image;

/**
 * Created by viet on 20/09/2017.
 */

public class OtherBoard {
    String uid;
    String boardKey;
    boolean isStar;
    public OtherBoard() {
    }


    public OtherBoard(String uid, String boardKey, boolean isStar) {
        this.uid = uid;
        this.boardKey = boardKey;
        this.isStar = isStar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBoardKey() {
        return boardKey;
    }

    public void setBoardKey(String boardKey) {
        this.boardKey = boardKey;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }
}
