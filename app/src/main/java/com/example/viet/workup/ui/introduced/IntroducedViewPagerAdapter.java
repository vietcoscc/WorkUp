package com.example.viet.workup.ui.introduced;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.viet.workup.R;
import com.example.viet.workup.ui.introduced.content.ContentFragment;
/**
 * Created by viet on 15/08/2017.
 */

public class IntroducedViewPagerAdapter extends FragmentPagerAdapter {
    public static final int COUNT = 3;

    public IntroducedViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ContentFragment.newInstance(R.layout.fragment_introduced);
            case 1:
                return ContentFragment.newInstance(R.layout.fragment_introduced_2);
            case 2:
                return ContentFragment.newInstance(R.layout.fragment_introduced_3);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
