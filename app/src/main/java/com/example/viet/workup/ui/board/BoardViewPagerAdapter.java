package com.example.viet.workup.ui.board;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.viet.workup.model.CardList;
import com.example.viet.workup.ui.board.card.CardFragment;

import java.util.ArrayList;


/**
 * Created by viet on 17/08/2017.
 */

public class BoardViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = "BoardViewPagerAdapter";
    private ArrayList<CardList> mArrCardList;
    private String boardKey;

    public BoardViewPagerAdapter(FragmentManager fm, String boardKey, ArrayList<CardList> arrCardList) {
        super(fm);
        this.boardKey = boardKey;
        this.mArrCardList = arrCardList;
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(boardKey, mArrCardList.get(position).getKey());
    }

    @Override
    public int getCount() {
        return mArrCardList.size();
    }


}
