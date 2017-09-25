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
    private ArrayList<String> mArrCardListKey;
    private String boardKey;
    private ArrayList<Fragment> arrFragment = new ArrayList<>();

    public BoardViewPagerAdapter(FragmentManager fm, String boardKey, ArrayList<CardList> arrCardList, ArrayList<String> arrCardListKey) {
        super(fm);
        this.boardKey = boardKey;
        this.mArrCardList = arrCardList;
        this.mArrCardListKey = arrCardListKey;
    }

    @Override
    public Fragment getItem(int position) {
        return arrFragment.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mArrCardList.size();
    }

    public void addItem(CardList cardList) {
        try {
            if (cardList != null) {
                mArrCardList.add(cardList);
                mArrCardListKey.add(cardList.getKey());
                arrFragment.add(CardFragment.newInstance(boardKey,cardList.getKey()));
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeItem(int position) {
        try {
            if (position > -1 && position < mArrCardList.size()) {
                mArrCardList.remove(position);
                mArrCardListKey.remove(position);
                arrFragment.remove(position);
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
