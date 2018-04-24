package com.oldandx.oldnx.view.discover;


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

import com.oldandx.oldnx.binding.FragmentDataBindingComponent;
import com.oldandx.oldnx.databinding.FragmentDiscoverBinding;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.utils.AutoClearedValue;
import com.oldandx.oldnx.view.common.BackHandledFragment;
import com.oldandx.oldnx.viewmodel.DiscoverViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends BackHandledFragment {

    private DiscoverViewModel mDiscoverViewModel;

    private FragmentActivity mFragmentActivity;

    private DataBindingComponent mDataBindingComponent = new FragmentDataBindingComponent(this);

    private AutoClearedValue<CategoryListAdapter> mAdapter;

    private AutoClearedValue<FragmentDiscoverBinding> mBinding;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = (FragmentActivity) activity;
    }

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDiscoverViewModel = ActivityUtils.obtainViewModel(mFragmentActivity, DiscoverViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentDiscoverBinding fragmentDiscoverBinding
                = FragmentDiscoverBinding.inflate(inflater, container, false, mDataBindingComponent);

        mBinding = new AutoClearedValue<>(this, fragmentDiscoverBinding);

        return fragmentDiscoverBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
        //subscribeToLiveData();
    }

    private void initUI() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        CategoryListAdapter adapter = new CategoryListAdapter(mDataBindingComponent);
        this.mAdapter = new AutoClearedValue<>(this, adapter);

        mBinding.get().rvCategoryList.setAdapter(adapter);

        initCategories();
    }

    private void initCategories() {
        List<String> categories = new ArrayList<>();

        categories.add("Properties");
        categories.add("Cars");
        categories.add("Electronics");
        categories.add("Furniture");
        categories.add("Jobs");
        categories.add("Fashion");
        categories.add("Bikes");
        categories.add("Books");
        categories.add("Mobiles");
        categories.add("Pets");
        categories.add("Services");
        categories.add("Donate");

        mAdapter.get().replace(categories);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
