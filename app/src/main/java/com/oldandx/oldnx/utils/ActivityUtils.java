package com.oldandx.oldnx.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.oldandx.oldnx.viewmodel.ViewModelFactory;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * This provides methods to help Activities load their UI.
 */
public final class ActivityUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private ActivityUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId,
                                             boolean isAddToBackStack, @Nullable String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (null != tag) {
            transaction.replace(frameId, fragment, tag);
        } else {
            transaction.replace(frameId, fragment);
        }
        if (isAddToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addNonUIFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment nonUIFragment, String fragmentTag) {
        checkNotNull(fragmentManager);
        checkNotNull(nonUIFragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(nonUIFragment, fragmentTag);
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivityWithSharedElement(@NonNull FragmentManager fragmentManager,
                                                              @NonNull Fragment fragment, int frameId,
                                                              View sharedElement, String transitionName,
                                                              boolean isAddToBackStack, @Nullable String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addSharedElement(sharedElement, transitionName);
        if (null != tag) {
            transaction.replace(frameId, fragment, tag);
        } else {
            transaction.replace(frameId, fragment);
        }
        if (isAddToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToContentContainer(@NonNull FragmentManager fragmentManager
            , @NonNull Fragment fragment, Fragment activeFragment, int frameId, @NonNull String tag, boolean hideShow) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        checkNotNull(tag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (hideShow) {
            transaction.hide(activeFragment);
            transaction.show(fragment);
        } else {
            if (null != activeFragment) {
                transaction.hide(activeFragment);
            }
            transaction.add(frameId, fragment, tag);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    /**
     * @param activity instance of parent activity.
     * @param type     ViewModel type.
     * @param <T>      ViewModel extension.
     * @return ViewModel.
     */
    public static <T extends ViewModel> T obtainViewModel(FragmentActivity activity, Class<T> type) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(type);
    }
}
