package com.oldandx.oldnx.view.boarding;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingComponent;
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
import com.oldandx.oldnx.databinding.FragmentBoardingLauncherBinding;
import com.oldandx.oldnx.utils.AutoClearedValue;
import com.oldandx.oldnx.view.login.LinkAccountActivity;
import com.oldandx.oldnx.view.common.pageindicator.CirclePageIndicator;

import java.util.Arrays;

public class BoardingLauncherFragment extends Fragment {


    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);

    private AutoClearedValue<FragmentBoardingLauncherBinding> mBinding;

    private BoardingViewPagerAdapter mPagerAdapter;

    private ViewPager mOnBoardingPager;

    private FragmentActivity mFragmentActivity;

    private static BoardingViewPagerAdapter.BoardingViewPagerModel[] BOARDING_MODELS = {
            new BoardingViewPagerAdapter.BoardingViewPagerModel(1),
            new BoardingViewPagerAdapter.BoardingViewPagerModel(2),
            new BoardingViewPagerAdapter.BoardingViewPagerModel(3),
            new BoardingViewPagerAdapter.BoardingViewPagerModel(4)
    };

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

        FragmentBoardingLauncherBinding fragmentBoardingLauncherBinding
                = FragmentBoardingLauncherBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, fragmentBoardingLauncherBinding);

        return fragmentBoardingLauncherBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribeViewEvents();

        initViewPager();
    }

    //region Private Methods
    private void initViewPager() {

        if (mPagerAdapter == null) {
            mPagerAdapter = new BoardingViewPagerAdapter(mFragmentActivity.getSupportFragmentManager(), Arrays.asList(BOARDING_MODELS));
        }

        // Set View Pager
        mOnBoardingPager = mBinding.get().vpBoarding;
        mOnBoardingPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        mOnBoardingPager.setAdapter(mPagerAdapter);
        mOnBoardingPager.addOnPageChangeListener(mOnBoardingPagerListener);

        CirclePageIndicator circlePageIndicator = mBinding.get().cpiWelcomeScreen;
        circlePageIndicator.setViewPager(mOnBoardingPager);
    }

    private ViewPager.OnPageChangeListener mOnBoardingPagerListener = new ViewPager.SimpleOnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {

            int currPage = mOnBoardingPager.getCurrentItem();
            int totalPages = BOARDING_MODELS.length;

            if (currPage == (totalPages - 1)) {
                mBinding.get().btnNext.setText(R.string.button_text_done);
                mBinding.get().btnSkip.setVisibility(View.GONE);
            } else {
                mBinding.get().btnSkip.setVisibility(View.VISIBLE);
                mBinding.get().btnNext.setText(R.string.button_text_next);
            }

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };


    private void subscribeViewEvents() {
        mBinding.get().btnNext.setOnClickListener(view
                -> walkThroughOnBoardingPages());

        mBinding.get().btnSkip.setOnClickListener(v
                -> navigateToLinkAccountActivity());
    }

    private void walkThroughOnBoardingPages() {
        int currPage = mOnBoardingPager.getCurrentItem();
        int totalPages = BOARDING_MODELS.length;

        if (mBinding.get().btnNext.getText().toString().equals(getResources().getString(R.string.button_text_done))) {
            navigateToLinkAccountActivity();
        }

        if (currPage < totalPages) {
            mOnBoardingPager.setCurrentItem(++currPage);
        }
    }

    private void navigateToLinkAccountActivity() {
        startActivity(new Intent(mFragmentActivity, LinkAccountActivity.class));
        mFragmentActivity.finish();
    }
    //endregion

}
