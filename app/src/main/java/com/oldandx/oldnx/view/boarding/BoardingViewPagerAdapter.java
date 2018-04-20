package com.oldandx.oldnx.view.boarding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Pardeep Sharma on 4/19/2018
 */

public class BoardingViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BoardingViewPagerModel> mBoardingImagesList;

    BoardingViewPagerAdapter(FragmentManager fm, List<BoardingViewPagerModel> boardingImages) {
        super(fm);
        mBoardingImagesList = boardingImages;
    }

    @Override
    public int getCount() {
        return mBoardingImagesList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return BoardingFragment.newInstance(position);
    }

    static class BoardingViewPagerModel {
        int pageNumber;

        BoardingViewPagerModel(int pageNumber) {
            this.pageNumber = pageNumber;
        }
    }
}
