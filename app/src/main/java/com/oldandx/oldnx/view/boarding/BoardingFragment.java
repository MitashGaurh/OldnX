package com.oldandx.oldnx.view.boarding;

import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.binding.FragmentDataBindingComponent;
import com.oldandx.oldnx.databinding.FragmentBoardingSlidePageBinding;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.utils.AutoClearedValue;

/**
 * Created by Pardeep Sharma on 4/19/2017
 */

public class BoardingFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private FragmentActivity mFragmentActivity;
    private FragmentBoardingSlidePageBinding mFragmentBoardingSlidePageBinding;
    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);
    private AutoClearedValue<FragmentBoardingSlidePageBinding> mBinding;

    private int mPageNumber;
    private ImageView imgViewWelcomeScreen;

    public static BoardingFragment create(int pageNumber) {
        BoardingFragment fragment = new BoardingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentBoardingSlidePageBinding
                = FragmentBoardingSlidePageBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, mFragmentBoardingSlidePageBinding);

        imgViewWelcomeScreen = mBinding.get().imgviewWelcomescreen;

        setPageContent();

        return mFragmentBoardingSlidePageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setPageContent(){
        switch (getPageNumber()){

            case 0:
                imgViewWelcomeScreen.setImageResource(ActivityUtils.getDrawableByName(mFragmentActivity, "screen_1"));
               // imgViewWelcomeScreen.setImageDrawable(AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.screen_1));
                break;

            case 1:
                imgViewWelcomeScreen.setImageResource(ActivityUtils.getDrawableByName(mFragmentActivity, "screen_1"));

                // imgViewWelcomeScreen.setImageDrawable(AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.screen_1));
                break;

            case 2:
                imgViewWelcomeScreen.setImageResource(ActivityUtils.getDrawableByName(mFragmentActivity, "screen_1"));

                // imgViewWelcomeScreen.setImageDrawable(AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.screen_1));
                break;

            case 3:
                imgViewWelcomeScreen.setImageResource(ActivityUtils.getDrawableByName(mFragmentActivity, "screen_1"));

                // imgViewWelcomeScreen.setImageDrawable(AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.screen_1));
                break;

        }
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
