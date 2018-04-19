package com.oldandx.oldnx.view.login;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.oldandx.oldnx.binding.FragmentDataBindingComponent;
import com.oldandx.oldnx.databinding.FragmentLinkAccountBinding;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.utils.AutoClearedValue;
import com.oldandx.oldnx.viewmodel.LinkAccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkAccountFragment extends Fragment {

    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);

    private FragmentLinkAccountBinding mFragmentLoginBinding;

    private AutoClearedValue<FragmentLinkAccountBinding> mBinding;

    private LinkAccountViewModel mLinkAccountViewModel;

    private FragmentActivity mFragmentActivity;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    public static LinkAccountFragment newInstance() {
        return new LinkAccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLinkAccountViewModel = ActivityUtils.obtainViewModel(mFragmentActivity, LinkAccountViewModel.class);

        subscribeToLiveData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentLoginBinding
                = FragmentLinkAccountBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, mFragmentLoginBinding);

        return mFragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentLoginBinding.setViewModel(mLinkAccountViewModel);
    }

    private void subscribeToLiveData() {

    }
}
