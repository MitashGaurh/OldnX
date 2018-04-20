package com.oldandx.oldnx.view.navigation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.databinding.ActivityNavigationBinding;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.view.common.BackHandledFragment;
import com.oldandx.oldnx.view.common.bottomnavigation.BottomNavigationBar;
import com.oldandx.oldnx.view.common.bottomnavigation.BottomNavigationItem;
import com.oldandx.oldnx.viewmodel.NavigationViewModel;
import com.oldandx.oldnx.vo.BottomTab;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener
        , BackHandledFragment.BackHandlerInterface {

    private ActivityNavigationBinding mActivityNavigationBinding;

    private NavigationViewModel mNavigationViewModel;

    private BackHandledFragment mSelectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);

        mNavigationViewModel = ActivityUtils.obtainViewModel(this, NavigationViewModel.class);

        initBottomNavigationBar();

        if (null == savedInstanceState) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
                    , NavigationFragment.newInstance()
                    , R.id.navigation_container
                    , false
                    , NavigationFragment.class.getSimpleName());
        }
    }

    @Override
    public void onBackPressed() {
        if (null == mSelectedFragment || !mSelectedFragment.onBackPressed()) {
            // Selected fragment did not consume the back press event.
            super.onBackPressed();
        }
    }

    private void initBottomNavigationBar() {
        mActivityNavigationBinding.bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mActivityNavigationBinding.bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mActivityNavigationBinding.bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_explore, "Discover")
                        .setActiveColorResource(R.color.colorPrimaryDark).setInActiveColorResource(R.color.colorTabUnselected))
                .addItem(new BottomNavigationItem(R.drawable.ic_chat, "Chat")
                        .setActiveColorResource(R.color.colorPrimaryDark).setInActiveColorResource(R.color.colorTabUnselected))
                .addItem(new BottomNavigationItem(R.drawable.ic_add_box, "Sell")
                        .setActiveColorResource(R.color.colorPrimaryDark).setInActiveColorResource(R.color.colorTabUnselected))
                .addItem(new BottomNavigationItem(R.drawable.ic_attach_money, "Price")
                        .setActiveColorResource(R.color.colorPrimaryDark).setInActiveColorResource(R.color.colorTabUnselected))
                .addItem(new BottomNavigationItem(R.drawable.ic_person, "Profile")
                        .setActiveColorResource(R.color.colorPrimaryDark).setInActiveColorResource(R.color.colorTabUnselected))
                .initialise();

        mActivityNavigationBinding.bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {

        BottomTab bottomTab = null;
        switch (position) {
            case 0:
                bottomTab = BottomTab.DISCOVER;
                break;
            case 1:
                bottomTab = BottomTab.CHAT;
                break;
            case 2:
                bottomTab = BottomTab.SELL;
                break;
            case 3:
                bottomTab = BottomTab.PRICE;
                break;
            case 4:
                bottomTab = BottomTab.PROFILE;
                break;
        }
        mNavigationViewModel.onBottomTabSelected(bottomTab);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.mSelectedFragment = backHandledFragment;
    }
}