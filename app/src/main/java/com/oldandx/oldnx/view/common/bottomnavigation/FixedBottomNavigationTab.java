package com.oldandx.oldnx.view.common.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.databinding.FixedBottomNavigationItemBinding;

/**
 * Created by Mitash Gaurh on 4/19/2018.
 */
public class FixedBottomNavigationTab extends BottomNavigationTab {

    private float mLabelScale;

    public FixedBottomNavigationTab(Context context) {
        super(context);
    }

    public FixedBottomNavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FixedBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init() {
        paddingTopActive = (int) getResources().getDimension(R.dimen.fixed_height_top_padding_active);
        paddingTopInActive = (int) getResources().getDimension(R.dimen.fixed_height_top_padding_inactive);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        FixedBottomNavigationItemBinding fixedBottomNavigationItemBinding
                = FixedBottomNavigationItemBinding.inflate(inflater,this,true);
        mContainerView = fixedBottomNavigationItemBinding.fixedBottomNavigationContainer;
        mLabelView = fixedBottomNavigationItemBinding.fixedBottomNavigationTitle;
        mIconView = fixedBottomNavigationItemBinding.fixedBottomNavigationIcon;
        mIconContainerView = fixedBottomNavigationItemBinding.fixedBottomNavigationIconContainer;
        mBadgeView = fixedBottomNavigationItemBinding.fixedBottomNavigationBadge;

        mLabelScale = getResources().getDimension(R.dimen.fixed_label_inactive) / getResources().getDimension(R.dimen.fixed_label_active);

        super.init();
    }

    @Override
    public void select(boolean setActiveColor, int animationDuration) {
        mLabelView.animate().scaleX(1).scaleY(1).setDuration(animationDuration).start();
        super.select(setActiveColor, animationDuration);
    }

    @Override
    public void unSelect(boolean setActiveColor, int animationDuration) {
        mLabelView.animate().scaleX(mLabelScale).scaleY(mLabelScale).setDuration(animationDuration).start();
        super.unSelect(setActiveColor, animationDuration);
    }

    @Override
    protected void setNoTitleIconContainerParams(FrameLayout.LayoutParams layoutParams) {
        layoutParams.height = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_container_height);
        layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_container_width);
    }

    @Override
    protected void setNoTitleIconParams(LayoutParams layoutParams) {
        layoutParams.height = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_height);
        layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_width);
    }
}
