package com.oldandx.oldnx.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.view.chat.ChatFragment;
import com.oldandx.oldnx.view.discover.DiscoverFragment;
import com.oldandx.oldnx.vo.BottomTab;
import com.oldandx.oldnx.vo.StackFragment;
import com.oldandx.oldnx.vo.event.SingleLiveEvent;

import java.util.Stack;

/**
 * Created by Mitash Gaurh on 4/20/2018.
 */
public class NavigationViewModel extends AndroidViewModel {

    private final Stack<Fragment> mDiscoverTabStack = new Stack<>();

    private final Stack<Fragment> mChatTabStack = new Stack<>();

    private final Stack<Fragment> mPriceTabStack = new Stack<>();

    private final Stack<Fragment> mProfileTabStack = new Stack<>();

    private final SingleLiveEvent<BottomTab> mBottomTabSelectedEvent = new SingleLiveEvent<>();

    private final SingleLiveEvent<StackFragment> mStackFragmentEvent = new SingleLiveEvent<>();

    private Fragment mActiveFragment;

    NavigationViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<BottomTab> getBottomTabSelectedEvent() {
        return mBottomTabSelectedEvent;
    }

    public SingleLiveEvent<StackFragment> getStackFragmentEvent() {
        return mStackFragmentEvent;
    }

    public void onBottomTabSelected(BottomTab bottomTab) {
        mBottomTabSelectedEvent.setValue(bottomTab);
    }

    public void performBottomTabFragmentsTransaction(FragmentManager fragmentManager, BottomTab bottomTab) {

        switch (bottomTab) {
            case DISCOVER:
                updateStackAlongWithManager(fragmentManager, mDiscoverTabStack, new DiscoverFragment());
                break;
            case CHAT:
                updateStackAlongWithManager(fragmentManager, mChatTabStack, new ChatFragment());
                break;
            case SELL:
                break;
            case PRICE:
                break;
            case PROFILE:
                break;
        }
    }

    public void performStackFragmentsTransaction(FragmentManager fragmentManager, StackFragment stackFragment) {

        switch (stackFragment.bottomTab) {
            case DISCOVER:
                updateStackAlongWithManager(fragmentManager, mDiscoverTabStack, stackFragment.fragment);
                break;
            case CHAT:
                updateStackAlongWithManager(fragmentManager, mChatTabStack, stackFragment.fragment);
                break;
            case SELL:
                break;
            case PRICE:
                break;
            case PROFILE:
                break;
        }
    }

    private void updateStackAlongWithManager(FragmentManager fragmentManager
            , Stack<Fragment> stack, Fragment fragment) {

        Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());

        if (null != fragmentByTag) {
            if (stack.search(fragmentByTag) != -1) {
                fragmentByTag = stack.peek();
            } else {
                stack.push(fragmentByTag);
            }
            ActivityUtils.addFragmentToContentContainer(fragmentManager
                    , fragmentByTag, mActiveFragment, R.id.content_container
                    , fragmentByTag.getClass().getSimpleName(), true);
            mActiveFragment = fragmentByTag;
        } else {
            stack.push(fragment);
            ActivityUtils.addFragmentToContentContainer(fragmentManager
                    , fragment, mActiveFragment, R.id.content_container
                    , fragment.getClass().getSimpleName(), false);
            mActiveFragment = fragment;
        }
    }
}
