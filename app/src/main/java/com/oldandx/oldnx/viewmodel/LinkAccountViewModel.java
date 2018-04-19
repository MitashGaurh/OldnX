package com.oldandx.oldnx.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.oldandx.oldnx.vo.event.SingleLiveEvent;

/**
 * Created by Mitash Gaurh on 4/18/2018.
 */
public class LinkAccountViewModel extends AndroidViewModel {

    private static final String TAG = "LinkAccountViewModel";

    private final SingleLiveEvent<Void> mContinueWithFaceBookEvent = new SingleLiveEvent<>();

    public LinkAccountViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<Void> getContinueWithFaceBookEvent() {
        return mContinueWithFaceBookEvent;
    }

    public void continueWithFacebook() {
        mContinueWithFaceBookEvent.call();
    }

    public void loginNow() {
        Log.d(TAG, "loginNow: ");
    }
}
