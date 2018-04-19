package com.oldandx.oldnx.view.splash;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldandx.oldnx.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

}
