package com.oldandx.oldnx.view.common.bottomnavigation;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mitash Gaurh on 4/19/2018.
 */
public abstract class BottomNavigationTab extends FrameLayout {

    protected boolean isNoTitleMode;

    protected int paddingTopActive;
    protected int paddingTopInActive;

    protected int mPosition;
    protected int mActiveColor;
    protected int mInActiveColor;
    protected int mBackgroundColor;
    protected int mActiveWidth;
    protected int mInActiveWidth;

    protected Drawable mCompactIcon;
    protected Drawable mCompactInActiveIcon;
    protected boolean isInActiveIconSet = false;
    protected String mLabel;

    protected BadgeItem mBadgeItem;

    boolean isActive = false;

    View mContainerView;
    TextView mLabelView;
    ImageView mIconView;
    FrameLayout mIconContainerView;
    BadgeTextView mBadgeView;

    public BottomNavigationTab(Context context) {
        this(context, null);
    }

    public BottomNavigationTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setIsNoTitleMode(boolean isNoTitleMode) {
        this.isNoTitleMode = isNoTitleMode;
    }

    public boolean getIsNoTitleMode() {
        return isNoTitleMode;
    }

    public void setActiveWidth(int activeWidth) {
        mActiveWidth = activeWidth;
    }

    public void setInactiveWidth(int inactiveWidth) {
        mInActiveWidth = inactiveWidth;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = mInActiveWidth;
        setLayoutParams(params);
    }

    public void setIcon(Drawable icon) {
        mCompactIcon = DrawableCompat.wrap(icon);
    }

    public void setInactiveIcon(Drawable icon) {
        mCompactInActiveIcon = DrawableCompat.wrap(icon);
        isInActiveIconSet = true;
    }

    public void setLabel(String label) {
        mLabel = label;
        mLabelView.setText(label);
    }

    public void setActiveColor(int activeColor) {
        mActiveColor = activeColor;
    }

    public int getActiveColor() {
        return mActiveColor;
    }

    public void setInactiveColor(int inActiveColor) {
        mInActiveColor = inActiveColor;
        mLabelView.setTextColor(inActiveColor);
    }

    public void setItemBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public void setBadgeItem(BadgeItem mBadgeItem) {
        this.mBadgeItem = mBadgeItem;
    }

    public int getPosition() {
        return mPosition;
    }

    public void select(boolean setActiveColor, int animationDuration) {
        isActive = true;

        ValueAnimator animator = ValueAnimator.ofInt(mContainerView.getPaddingTop(), paddingTopActive);
        animator.addUpdateListener(valueAnimator
                -> mContainerView.setPadding(mContainerView.getPaddingLeft(),
                (Integer) valueAnimator.getAnimatedValue(),
                mContainerView.getPaddingRight(),
                mContainerView.getPaddingBottom()));
        animator.setDuration(animationDuration);
        animator.start();

        mIconView.setSelected(true);
        if (setActiveColor) {
            mLabelView.setTextColor(mActiveColor);
        } else {
            mLabelView.setTextColor(mBackgroundColor);
        }

        if (mBadgeItem != null) {
            mBadgeItem.select();
        }
    }

    public void unSelect(boolean setActiveColor, int animationDuration) {
        isActive = false;

        ValueAnimator animator = ValueAnimator.ofInt(mContainerView.getPaddingTop(), paddingTopInActive);
        animator.addUpdateListener(valueAnimator
                -> mContainerView.setPadding(mContainerView.getPaddingLeft(),
                (Integer) valueAnimator.getAnimatedValue(),
                mContainerView.getPaddingRight(),
                mContainerView.getPaddingBottom()));
        animator.setDuration(animationDuration);
        animator.start();

        mLabelView.setTextColor(mInActiveColor);
        mIconView.setSelected(false);

        if (mBadgeItem != null) {
            mBadgeItem.unSelect();
        }
    }

    @CallSuper
    public void initialise(boolean setActiveColor) {
        mIconView.setSelected(false);
        if (isInActiveIconSet) {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_selected},
                    mCompactIcon);
            states.addState(new int[]{-android.R.attr.state_selected},
                    mCompactInActiveIcon);
            states.addState(new int[]{},
                    mCompactInActiveIcon);
            mIconView.setImageDrawable(states);
        } else {
            if (setActiveColor) {
                DrawableCompat.setTintList(mCompactIcon, new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected}, //1
                                new int[]{-android.R.attr.state_selected}, //2
                                new int[]{}
                        },
                        new int[]{
                                mActiveColor, //1
                                mInActiveColor, //2
                                mInActiveColor //3
                        }
                ));
            } else {
                DrawableCompat.setTintList(mCompactIcon, new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected}, //1
                                new int[]{-android.R.attr.state_selected}, //2
                                new int[]{}
                        },
                        new int[]{
                                mBackgroundColor, //1
                                mInActiveColor, //2
                                mInActiveColor //3
                        }
                ));
            }
            mIconView.setImageDrawable(mCompactIcon);
        }

        if (isNoTitleMode) {
            mLabelView.setVisibility(GONE);

            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIconContainerView.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            setNoTitleIconContainerParams(layoutParams);
            mIconContainerView.setLayoutParams(layoutParams);

            FrameLayout.LayoutParams iconLayoutParams = (FrameLayout.LayoutParams) mIconView.getLayoutParams();
            setNoTitleIconParams(iconLayoutParams);
            mIconView.setLayoutParams(iconLayoutParams);
        }
    }

    protected abstract void setNoTitleIconContainerParams(FrameLayout.LayoutParams layoutParams);

    protected abstract void setNoTitleIconParams(FrameLayout.LayoutParams layoutParams);
}