package com.oldandx.oldnx.view.login;


import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldandx.oldnx.binding.FragmentDataBindingComponent;
import com.oldandx.oldnx.databinding.FragmentLoginBinding;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.utils.AutoClearedValue;
import com.oldandx.oldnx.viewmodel.LinkAccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);

    private FragmentLoginBinding mFragmentLoginBinding;

    private LinkAccountViewModel mLinkAccountViewModel;

    private AutoClearedValue<FragmentLoginBinding> mBinding;

    private FragmentActivity mFragmentActivity;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLinkAccountViewModel = ActivityUtils.obtainViewModel(mFragmentActivity, LinkAccountViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentLoginBinding
                = FragmentLoginBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, mFragmentLoginBinding);

        return mFragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentLoginBinding.setViewModel(mLinkAccountViewModel);

        ViewCompat.setTransitionName(mBinding.get().ivSharedLogo, "logoTransition");
    }

}
