package com.oldandx.oldnx.view.navigation;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.databinding.ActivityNavigationBinding;
import com.oldandx.oldnx.view.common.bottomnavigation.BottomNavigationBar;
import com.oldandx.oldnx.view.common.bottomnavigation.BottomNavigationItem;

public class NavigationActivity extends Activity {

    private ActivityNavigationBinding mActivityNavigationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);

        mActivityNavigationBinding.bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mActivityNavigationBinding.bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mActivityNavigationBinding.bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_explore, "Explore")
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
    }
}
