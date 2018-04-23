package com.oldandx.oldnx;

import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.oldandx.oldnx.utils.FileLoggingTree;

import java.io.PrintWriter;
import java.io.StringWriter;

import timber.log.Timber;

/**
 * Created by Pardeep Sharma on 4/17/2018.
 */

public class OldnXApplication extends MultiDexApplication {

    public static void plantTimberFileLogging() {
        Timber.plant(new FileLoggingTree());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {

            StringWriter sw = new StringWriter();
            exception.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Timber.e("  ---->  %s", exceptionAsString);
            Timber.e("uncaughtException: Exception ENDS ##############");
        });

        initializeTimber();
        //provideDatabase();
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
