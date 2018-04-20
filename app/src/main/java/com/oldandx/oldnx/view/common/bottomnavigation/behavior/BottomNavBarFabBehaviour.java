package com.oldandx.oldnx.view.common.bottomnavigation.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

import com.oldandx.oldnx.view.common.bottomnavigation.BottomNavigationBar;

import java.util.List;

/**
 * Created by Mitash Gaurh on 4/19/2018.
 */
public class BottomNavBarFabBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();

    private ViewPropertyAnimatorCompat mFabTranslationYAnimator;

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return isDependent(dependency) || super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);

        updateFabTranslationForBottomNavigationBar(parent, child, null);

        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if (isDependent(dependency)) {
            updateFabTranslationForBottomNavigationBar(parent, child, dependency);
            return false;
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, FloatingActionButton child,
                                       View dependency) {
        if (isDependent(dependency)) {
            updateFabTranslationForBottomNavigationBar(parent, child, dependency);
        }
    }

    private boolean isDependent(View dependency) {
        return dependency instanceof BottomNavigationBar || dependency instanceof Snackbar.SnackbarLayout;
    }

    private void updateFabTranslationForBottomNavigationBar(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {

        float snackBarTranslation = getFabTranslationYForSnackBar(parent, fab);
        float[] bottomBarParameters = getFabTranslationYForBottomNavigationBar(parent, fab);
        float bottomBarTranslation = bottomBarParameters[0];
        float bottomBarHeight = bottomBarParameters[1];

        float targetTransY = 0;
        if (snackBarTranslation >= bottomBarTranslation) {
            // when snackBar is below BottomBar in translation present.
            targetTransY = bottomBarTranslation;
        } else {
            targetTransY = snackBarTranslation;
        }

        final float currentTransY = ViewCompat.getTranslationY(fab);

        // Make sure that any current animation is cancelled
        ensureOrCancelAnimator(fab);


        if (fab.isShown()
                && Math.abs(currentTransY - targetTransY) > (fab.getHeight() * 0.667f)) {
            // If the FAB will be travelling by more than 2/3 of it's height, let's animate it instead
            mFabTranslationYAnimator.translationY(targetTransY).start();
        } else {
            // Now update the translation Y
            ViewCompat.setTranslationY(fab, targetTransY);
        }
    }

    private float[] getFabTranslationYForBottomNavigationBar(CoordinatorLayout parent,
                                                             FloatingActionButton fab) {
        float minOffset = 0;
        float viewHeight = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof BottomNavigationBar) {
                viewHeight = view.getHeight();
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - viewHeight);
            }
        }

        return new float[]{minOffset, viewHeight};
    }

    private float getFabTranslationYForSnackBar(CoordinatorLayout parent,
                                                FloatingActionButton fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - view.getHeight());
            }
        }

        return minOffset;
    }

    private void ensureOrCancelAnimator(FloatingActionButton fab) {
        if (mFabTranslationYAnimator == null) {
            mFabTranslationYAnimator = ViewCompat.animate(fab);
            mFabTranslationYAnimator.setDuration(400);
            mFabTranslationYAnimator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
        } else {
            mFabTranslationYAnimator.cancel();
        }
    }
}
