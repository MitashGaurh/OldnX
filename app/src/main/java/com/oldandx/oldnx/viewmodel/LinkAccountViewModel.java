package com.oldandx.oldnx.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.oldandx.oldnx.vo.event.SingleLiveEvent;

/**
 * Created by Mitash Gaurh on 4/18/2018.
 */
public class LinkAccountViewModel extends AndroidViewModel {

    private static final String TAG = "LinkAccountViewModel";

    private final SingleLiveEvent<Void> mContinueWithFaceBookEvent = new SingleLiveEvent<>();

    private final SingleLiveEvent<Void> mLoginNowEvent = new SingleLiveEvent<>();

    LinkAccountViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<Void> getContinueWithFaceBookEvent() {
        return mContinueWithFaceBookEvent;
    }

    public SingleLiveEvent<Void> getLoginNowEvent() {
        return mLoginNowEvent;
    }

    public void continueWithFacebook() {
        mContinueWithFaceBookEvent.call();
    }

    public void loginNow() {
        mLoginNowEvent.call();
    }
}
