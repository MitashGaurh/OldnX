package com.oldandx.oldnx.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

/**
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LinkAccountViewModel.class)) {
            //noinspection unchecked
            return (T) new LinkAccountViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(NavigationViewModel.class)) {
            //noinspection unchecked
            return (T) new NavigationViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(DiscoverViewModel.class)) {
            //noinspection unchecked
            return (T) new DiscoverViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            //noinspection unchecked
            return (T) new ProfileViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
