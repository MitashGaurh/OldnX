package com.oldandx.oldnx.vo;

import android.support.v4.app.Fragment;

/**
 * Created by Mitash Gaurh on 4/20/2018.
 */
public class StackFragment {

    public final BottomTab bottomTab;

    public final Fragment fragment;

    public StackFragment(BottomTab bottomTab, Fragment fragment) {
        this.bottomTab = bottomTab;
        this.fragment = fragment;
    }
}
