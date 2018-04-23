package com.oldandx.oldnx.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mitash Gaurh on 4/13/2018.
 */

@SuppressLint("MissingPermission")
public class LocationProvider {

    private static final int TIMER_DELAY = 60000;

    private static final int LOCATION_UPDATE_MIN_DISTANCE = 10;

    private static final int LOCATION_UPDATE_MIN_TIME = 5000;

    private Timer mTimer;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private LocationManager mLocationManager;

    private LocationResult mLocationResult;

    private boolean mGpsEnabled = false;

    private boolean mNetworkEnabled = false;

    private static LocationProvider INSTANCE;

    private LocationProvider() {
    }

    public static LocationProvider getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new LocationProvider();
        }
        return INSTANCE;
    }

    public void getCurrentLocation(Context context, LocationResult locationResult) {
        //use LocationResult callback class to pass location value from LocationProvider to user code.
        mLocationResult = locationResult;

        if (null == mFusedLocationProviderClient) {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }

        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                mLocationResult.onLocationSuccess(task.getResult());
            } else {
                fetchLocationUsingLocationManager(context);
            }
        });
    }

    private void fetchLocationUsingLocationManager(Context context) {
        //exceptions will be thrown if provider is not permitted.

        if (null == mLocationManager) {
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        if (null != mLocationManager) {
            try {
                mGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                mNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //don't start listeners if no provider is enabled
            if (!mGpsEnabled && !mNetworkEnabled) {
                mLocationResult.onLocationFailure();
            }

            Location location = null;
            if (mGpsEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mGPSLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (mNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mNetworkLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (location != null) {
                mLocationResult.onLocationSuccess(location);
            } else {
                mTimer = new Timer();
                mTimer.schedule(new GetLastKnownLocation(), TIMER_DELAY);
            }
        }
    }

    private LocationListener mGPSLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (null != mTimer) {
                mTimer.cancel();
                mLocationResult.onLocationSuccess(location);
            }
            mLocationManager.removeUpdates(this);
            mLocationManager.removeUpdates(mNetworkLocationListener);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private LocationListener mNetworkLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (null != mTimer) {
                mTimer.cancel();
                mLocationResult.onLocationSuccess(location);
            }
            mLocationManager.removeUpdates(this);
            mLocationManager.removeUpdates(mGPSLocationListener);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastKnownLocation extends TimerTask {
        @Override
        public void run() {
            mLocationManager.removeUpdates(mGPSLocationListener);
            mLocationManager.removeUpdates(mNetworkLocationListener);

            Location net_loc = null, gps_loc = null;
            if (mGpsEnabled)
                gps_loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (mNetworkEnabled)
                net_loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            //if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime())
                    mLocationResult.onLocationSuccess(gps_loc);
                else
                    mLocationResult.onLocationSuccess(net_loc);
                return;
            }

            if (gps_loc != null) {
                mLocationResult.onLocationSuccess(gps_loc);
                return;
            }
            if (net_loc != null) {
                mLocationResult.onLocationSuccess(net_loc);
                return;
            }
            mLocationResult.onLocationSuccess(null);
        }
    }

    public static abstract class LocationResult {

        public abstract void onLocationSuccess(Location location);

        public abstract void onLocationFailure();

    }
}
