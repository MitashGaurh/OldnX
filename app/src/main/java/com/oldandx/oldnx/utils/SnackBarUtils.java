package com.oldandx.oldnx.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Provides a method to show a Snackbar.
 */
public class SnackBarUtils {

    public static void showSnackBar(View v, String snackBarText) {
        if (v == null || snackBarText == null) {
            return;
        }
        Snackbar.make(v, snackBarText, Snackbar.LENGTH_LONG).show();
    }
}