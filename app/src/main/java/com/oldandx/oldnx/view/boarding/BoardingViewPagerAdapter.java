package com.oldandx.oldnx.view.boarding;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Pardeep Sharma on 4/19/2018
 */

public class BoardingViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BoardingViewPagerModel> welcomePhotos;
    private Context mContext;

    public static class BoardingViewPagerModel {
        int pageNumber;


        public BoardingViewPagerModel(int pageNumber) {
            this.pageNumber = pageNumber;
        }
    }

    public BoardingViewPagerAdapter(FragmentManager fm, Context context, List<BoardingViewPagerModel> images) {
        super(fm);

        mContext = context;
        welcomePhotos = images;
    }

    @Override
    public int getCount() {
        return welcomePhotos.size();
    }

    @Override
    public Fragment getItem(int position) {
        return BoardingFragment.create(position);
    }
}
