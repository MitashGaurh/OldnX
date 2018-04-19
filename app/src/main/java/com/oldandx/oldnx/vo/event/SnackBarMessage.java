package com.oldandx.oldnx.vo.event;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.StringRes;

/**
 * A SingleLiveEvent used for Snackbar messages. Like a {@link SingleLiveEvent} but also prevents
 * null messages and uses a custom observer.
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class SnackBarMessage extends SingleLiveEvent<Integer> {

    public void observe(LifecycleOwner owner, final SnackBarObserver observer) {
        super.observe(owner, t -> {
            if (t == null) {
                return;
            }
            observer.onNewMessage(t);
        });
    }

    public interface SnackBarObserver {
        /**
         * Called when there is a new message to be shown.
         *
         * @param snackBarMessageResourceId The new message, non-null.
         */
        void onNewMessage(@StringRes int snackBarMessageResourceId);
    }

}