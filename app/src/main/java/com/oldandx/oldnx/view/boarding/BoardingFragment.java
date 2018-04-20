package com.oldandx.oldnx.view.boarding;

import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.binding.FragmentDataBindingComponent;
import com.oldandx.oldnx.databinding.FragmentBoardingBinding;
import com.oldandx.oldnx.utils.AutoClearedValue;

/**
 * Created by Pardeep Sharma on 4/19/2017
 */

public class BoardingFragment extends Fragment {

    public static final String ARG_PAGE_NUMBER = "argPageNumber";

    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);

    private AutoClearedValue<FragmentBoardingBinding> mBinding;

    private int mPageNumber;

    public static BoardingFragment newInstance(int pageNumber) {
        BoardingFragment fragment = new BoardingFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getArguments()) {
            mPageNumber = getArguments().getInt(ARG_PAGE_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentBoardingBinding fragmentBoardingSlidePageBinding
                = FragmentBoardingBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, fragmentBoardingSlidePageBinding);

        initPageContent();

        return fragmentBoardingSlidePageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initPageContent() {

        String drawableName = "screen_1";

        switch (getPageNumber()) {
            case 0:
                drawableName = "screen_1";
                break;

            case 1:
                drawableName = "screen_1";
                break;

            case 2:
                drawableName = "screen_1";
                break;

            case 3:
                drawableName = "screen_1";
                break;

        }
        mBinding.get().setResourceId(R.drawable.screen_1);
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
