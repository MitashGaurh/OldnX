package com.oldandx.oldnx.utils;

import android.content.Context;
import android.content.res.Resources;

import com.oldandx.oldnx.R;

/**
 * Created by Mitash Gaurh on 4/20/2018.
 */
public final class AppUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private AppUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static int getDrawableByName(Context context, String drawableName) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(drawableName, "drawable",
                context.getPackageName());

        if (resourceId > 0) {
            return resourceId;
        }
        return R.drawable.bg_white;
    }
}
