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
import com.oldandx.oldnx.viewmodel.NavigationViewModel;
import com.oldandx.oldnx.vo.BottomTab;
import com.oldandx.oldnx.vo.StackFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private NavigationViewModel mNavigationViewModel;

    private FragmentActivity mFragmentActivity;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        rootView.findViewById(R.id.btn_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationViewModel.getStackFragmentEvent().setValue(new StackFragment(BottomTab.PROFILE, AccountFragment.newInstance()));
            }
        });

        return rootView;
    }

}
