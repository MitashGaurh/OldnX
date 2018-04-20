package com.oldandx.oldnx.view.navigation;


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
import com.oldandx.oldnx.viewmodel.NavigationViewModel;
import com.oldandx.oldnx.vo.BottomTab;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {

    private NavigationViewModel mNavigationViewModel;

    private FragmentActivity mFragmentActivity;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigationViewModel = ActivityUtils.obtainViewModel(mFragmentActivity, NavigationViewModel.class);

        subscribeViewEvent();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavigationViewModel.performBottomTabFragmentsTransaction(getChildFragmentManager(), BottomTab.DISCOVER);
    }

    private void subscribeViewEvent() {
        mNavigationViewModel.getBottomTabSelectedEvent().observe(this
                , bottomTab -> mNavigationViewModel.performBottomTabFragmentsTransaction(getChildFragmentManager()
                        , bottomTab));

        mNavigationViewModel.getStackFragmentEvent().observe(this
                , stackFragment -> mNavigationViewModel.performStackFragmentsTransaction(getChildFragmentManager()
                        , stackFragment));

    }
}
