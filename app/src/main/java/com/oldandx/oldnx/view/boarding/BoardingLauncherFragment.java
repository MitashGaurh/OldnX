package com.oldandx.oldnx.view.boarding;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.binding.FragmentDataBindingComponent;
import com.oldandx.oldnx.databinding.FragmentBoardingBinding;
import com.oldandx.oldnx.utils.AutoClearedValue;
import com.oldandx.oldnx.view.login.LinkAccountActivity;
import com.oldandx.oldnx.view.widgets.pagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardingLauncherFragment extends Fragment {

    private FragmentActivity mFragmentActivity;
    private FragmentBoardingBinding mFragmentBoardingBinding;
    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);
    private AutoClearedValue<FragmentBoardingBinding> mBinding;
    private BoardingViewPagerAdapter mPagerAdapter = null;
    private ViewPager mOnBoardingPager = null;
    private int mSelectedPageIndex = -1;
    private boolean mExitWhenScrollNextPage = false;

    public static BoardingLauncherFragment newInstance() {
        return new BoardingLauncherFragment();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentBoardingBinding
                = FragmentBoardingBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, mFragmentBoardingBinding);

        return mFragmentBoardingBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeUIEvents();
        initViewPager();
       // mFragmentBoardingBinding.setViewModel(mLinkAccountViewModel);
    }

    private static BoardingViewPagerAdapter.BoardingViewPagerModel[] arrBoardingModel = {
            new BoardingViewPagerAdapter.BoardingViewPagerModel(1),
            new BoardingViewPagerAdapter.BoardingViewPagerModel(2),
            new BoardingViewPagerAdapter.BoardingViewPagerModel(3),
            new BoardingViewPagerAdapter.BoardingViewPagerModel(4)
    };

    //region Private Methods
    private void initViewPager() {
        List<BoardingViewPagerAdapter.BoardingViewPagerModel> images  = new ArrayList<>();
        Collections.addAll(images, arrBoardingModel);

        mPagerAdapter = new BoardingViewPagerAdapter(mFragmentActivity.getSupportFragmentManager() , mFragmentActivity, images);

        // Set View Pager
        mOnBoardingPager = mBinding.get().vpOnBoardJourn;
        mOnBoardingPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        mOnBoardingPager.setAdapter(mPagerAdapter);
        mOnBoardingPager.addOnPageChangeListener(pagerListener);

        CirclePageIndicator mCirclePageIndicator = mBinding.get().cpiWelcomeScreen;
        mCirclePageIndicator.setViewPager(mOnBoardingPager);
    }

    private ViewPager.OnPageChangeListener pagerListener = new ViewPager.SimpleOnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            mSelectedPageIndex = position;

            int currPage = mOnBoardingPager.getCurrentItem();
            int totalPages = arrBoardingModel.length;

            if(currPage == (totalPages-1)) {
                mBinding.get().btnNext.setText(R.string.button_text_done);
            }else{

                mBinding.get().btnNext.setText(R.string.button_text_next);
            }

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mExitWhenScrollNextPage && position == mPagerAdapter.getCount() - 1) {
                mExitWhenScrollNextPage = false; // avoid call more times
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);

            if (state == ViewPager.SCROLL_STATE_IDLE) {
                mExitWhenScrollNextPage = mSelectedPageIndex == mPagerAdapter.getCount() - 1;
            }
        }
    };


    private void observeUIEvents() {
        mBinding.get().btnNext.setOnClickListener(view -> {
            walkThroughOnBoardingPages();
        });
    }

    private void walkThroughOnBoardingPages() {
        int currPage = mOnBoardingPager.getCurrentItem();
        int totalPages = arrBoardingModel.length;

        if(mBinding.get().btnNext.getText().toString().equals(getResources().getString(R.string.button_text_done))){
            mFragmentActivity.getSupportFragmentManager().popBackStackImmediate();

            startActivity(new Intent(mFragmentActivity, LinkAccountActivity.class));
            mFragmentActivity.finish();
        }

        if(currPage < totalPages){
            mOnBoardingPager.setCurrentItem(++currPage);
        }
    }
    //endregion

}
