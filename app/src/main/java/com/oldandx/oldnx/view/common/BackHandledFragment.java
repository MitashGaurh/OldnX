package com.oldandx.oldnx.view.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Mitash Gaurh on 4/20/2018.
 */
public abstract class BackHandledFragment extends Fragment {

    protected BackHandlerInterface mBackHandlerInterface;

    public abstract boolean onBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            mBackHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // Mark this fragment as the selected Fragment.
        mBackHandlerInterface.setSelectedFragment(this);
    }

    public interface BackHandlerInterface {

        void setSelectedFragment(BackHandledFragment backHandledFragment);
    }
}
