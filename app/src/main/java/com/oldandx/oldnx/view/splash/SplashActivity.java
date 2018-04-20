package com.oldandx.oldnx.view.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.oldandx.oldnx.OldnXApplication;
import com.oldandx.oldnx.R;
import com.oldandx.oldnx.utils.ActivityUtils;
import com.oldandx.oldnx.view.boarding.BoardingLauncherFragment;

public class SplashActivity extends AppCompatActivity {

    private static final int EXTERNAL_STORAGE_REQUEST_CODE = 1001;

    public static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (null == savedInstanceState) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
                    , SplashFragment.newInstance()
                    , R.id.splash_container
                    , false
                    , SplashFragment.class.getSimpleName());
        }

        checkPermissionForTimberFilePlanting();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    OldnXApplication.plantTimberFileLogging();
                }
                break;
            }
        }
        moveToLinkAccountActivity();
    }

    //region Private Methods
    private void checkPermissionForTimberFilePlanting() {
        if (ActivityCompat.checkSelfPermission(this
                , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            OldnXApplication.plantTimberFileLogging();
            moveToLinkAccountActivity();
        } else {
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    private void moveToLinkAccountActivity() {
        new Handler().postDelayed(()
                -> {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
                    , BoardingLauncherFragment.newInstance()
                    , R.id.splash_container
                    , false
                    , BoardingLauncherFragment.class.getSimpleName());
        }, 3000);
    }
    //endregion
}
