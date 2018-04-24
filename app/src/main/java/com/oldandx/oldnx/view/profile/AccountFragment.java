package com.oldandx.oldnx.view.profile;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.view.common.BackHandledFragment;
import com.oldandx.oldnx.viewmodel.NavigationViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BackHandledFragment {

    private NavigationViewModel mNavigationViewModel;

    private FragmentActivity mFragmentActivity;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigationViewModel = ActivityUtils.obtainViewModel(mFragmentActivity, NavigationViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return mNavigationViewModel.popProfileBackStack();
    }
}
