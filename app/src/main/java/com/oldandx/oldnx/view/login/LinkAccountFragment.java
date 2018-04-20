package com.oldandx.oldnx.view.login;


import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.binding.FragmentDataBindingComponent;
import com.oldandx.oldnx.databinding.FragmentLinkAccountBinding;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.utils.AutoClearedValue;
import com.oldandx.oldnx.view.common.DetailsTransition;
import com.oldandx.oldnx.viewmodel.LinkAccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkAccountFragment extends Fragment {

    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);

    private FragmentLinkAccountBinding mFragmentLinkAccountBinding;

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

        subscribeToViewEvent();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentLinkAccountBinding
                = FragmentLinkAccountBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, mFragmentLinkAccountBinding);

        return mFragmentLinkAccountBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentLinkAccountBinding.setViewModel(mLinkAccountViewModel);

        ViewCompat.setTransitionName(mBinding.get().ivSharedLogo, "logoTransition");
    }

    private void subscribeToViewEvent() {
        mLinkAccountViewModel.getAccountExistEvent().observe(this
                , aVoid -> {
                    LoginFragment loginFragment = LoginFragment.newInstance();
                    loginFragment.setSharedElementEnterTransition(new DetailsTransition());
                    loginFragment.setExitTransition(new Fade());
                    loginFragment.setSharedElementReturnTransition(null);

                    ActivityUtils.addFragmentToActivityWithSharedElement(mFragmentActivity.getSupportFragmentManager()
                            , loginFragment
                            , R.id.link_account_container
                            , mBinding.get().ivSharedLogo
                            , "logoTransition"
                            , true
                            , LoginFragment.class.getSimpleName());
                });
    }
}
