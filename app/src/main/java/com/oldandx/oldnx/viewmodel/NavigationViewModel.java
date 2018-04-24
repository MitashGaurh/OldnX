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
import com.oldandx.oldnx.view.profile.ProfileFragment;
import com.oldandx.oldnx.vo.BottomTab;
import com.oldandx.oldnx.vo.StackFragment;
import com.oldandx.oldnx.vo.event.SingleLiveEvent;

import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

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

    private FragmentManager mFragmentManager;

    NavigationViewModel(@NonNull Application application) {
        super(application);
    }

    public void initFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = checkNotNull(fragmentManager, "fragmentManager cannot be null.");
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

    public void performBottomTabFragmentsTransaction(BottomTab bottomTab) {

        switch (bottomTab) {
            case DISCOVER:
                updateStackAlongWithManager(mDiscoverTabStack, DiscoverFragment.newInstance(), true);
                break;
            case CHAT:
                updateStackAlongWithManager(mChatTabStack, new ChatFragment(), true);
                break;
            case SELL:
                break;
            case PRICE:
                break;
            case PROFILE:
                updateStackAlongWithManager(mProfileTabStack, new ProfileFragment(), true);
                break;
        }
    }

    public void performStackFragmentsTransaction(StackFragment stackFragment) {

        Stack<Fragment> stack = null;

        switch (stackFragment.bottomTab) {
            case DISCOVER:
                stack = mDiscoverTabStack;
                break;

            case CHAT:
                stack = mChatTabStack;
                break;

            case SELL:
                break;

            case PRICE:
                break;

            case PROFILE:
                stack = mProfileTabStack;
                break;
        }
        updateStackAlongWithManager(stack, stackFragment.fragment, false);
    }

    private void updateStackAlongWithManager(Stack<Fragment> stack, Fragment fragment, boolean isFromBottomTabs) {

        Fragment fragmentByTag = mFragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());

        if (null != fragmentByTag) {
            if (isFromBottomTabs) {
                if (stack.search(fragmentByTag) != -1) {
                    fragmentByTag = stack.peek();
                } else {
                    stack.push(fragmentByTag);
                }
            } else {
                fragmentByTag = fragment;
                stack.push(fragmentByTag);
            }
            performFragmentTransaction(fragmentByTag, true);
        } else {
            stack.push(fragment);
            performFragmentTransaction(fragment, false);
        }
    }

    public boolean popProfileBackStack() {
        if (mProfileTabStack.size() > 1) {
            backStackOperation(mProfileTabStack);
            return true;
        } else {
            return false;
        }
    }

    private void backStackOperation(Stack<Fragment> stack) {
        mFragmentManager.beginTransaction().remove(stack.pop()).commit();
        performFragmentTransaction(stack.peek(), true);
    }

    private void performFragmentTransaction(Fragment fragment, boolean isHideShow) {
        ActivityUtils.addFragmentToContentContainer(mFragmentManager
                , fragment, mActiveFragment, R.id.content_container
                , fragment.getClass().getSimpleName(), isHideShow);
        mActiveFragment = fragment;
    }
}
