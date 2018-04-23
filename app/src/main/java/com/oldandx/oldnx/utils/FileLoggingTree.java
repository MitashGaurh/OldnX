package com.oldandx.oldnx.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Pardeep Sharma on 12/12/2017.
 * This class saves daily logs into a HTML file under downloads folder(internal storage).
 */

public class FileLoggingTree extends Timber.DebugTree {

    private static final String TAG = FileLoggingTree.class.getSimpleName();

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {

        if (priority == Log.VERBOSE) {
            return;
        }

        try {

            String directoryName = "/OldnXAppLogs";

            File direct = new File(Environment.getExternalStorageDirectory() + File.separator + directoryName);

            if (!direct.exists()) {
                if (direct.mkdir()) {


                    String fileNameTimeStamp = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String logTimeStamp = new SimpleDateFormat("E MMM dd yyyy 'at' hh:mm:ss:SSS aaa", Locale.getDefault()).format(new Date
                            ());

                    String fileName = fileNameTimeStamp + ".html";

                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + directoryName + File.separator + fileName);

                    if (file.createNewFile() && file.exists()) {

                        OutputStream fileOutputStream = new FileOutputStream(file, true);

                        if (priority == Log.DEBUG)
                            fileOutputStream.write(("<p style=\"background:lightgray;\"><strong style=\"background:lightblue;\">&nbsp&nbsp" + logTimeStamp + " :&nbsp&nbsp</strong>&nbsp&nbsp" + message + "</p>").getBytes());
                        else
                            fileOutputStream.write(("<p style=\"background:lightgray;\"><strong style=\"background:red;\">&nbsp&nbsp" + logTimeStamp + " :&nbsp&nbsp</strong>&nbsp&nbsp" + message + "</p>").getBytes());

                        fileOutputStream.close();

                    }
                }
            }
        } catch (Exception e) {
            Timber.e(e, "Error while logging into file");
        }

    }
}
