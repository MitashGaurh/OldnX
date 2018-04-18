package com.oldandx.oldnx.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

/**
 * Shared preferences configuration utils
 */
@SuppressLint("CommitPrefEdits")
public final class PreferenceUtils {

    private final static boolean DEBUG = true;
    private final static String TAG = PreferenceUtils.class.getSimpleName();
    private static final Method APPLY_METHOD = findApplyMethod();

    /**
     * Don't let anyone instantiate this class.
     */
    private PreferenceUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static boolean contains(Context context, int resId) {
        return contains(context, context.getString(resId));
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.contains(key);
    }

    public static void remove(Context context, int resId) {
        remove(context, context.getString(resId));
    }

    public static void remove(Context context, String key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.remove(key);
        commitOrApply(editor);
    }

    public static void set(Context context, int resId, boolean value) {
        set(context, context.getString(resId), value);
    }

    public static void set(Context context, String key, boolean value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        commitOrApply(editor);
    }

    public static void set(Context context, int resId, float value) {
        set(context, context.getString(resId), value);
    }

    public static void set(Context context, String key, float value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putFloat(key, value);
        commitOrApply(editor);
    }

    public static void set(Context context, int resId, int value) {
        set(context, context.getString(resId), value);
    }

    public static void set(Context context, String key, int value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putInt(key, value);
        commitOrApply(editor);
    }

    public static void set(Context context, String key, Set<String> value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putStringSet(key, value);
        commitOrApply(editor);
    }

    public static void set(Context context, int resId, long value) {
        set(context, context.getString(resId), value);
    }

    public static void set(Context context, String key, long value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putLong(key, value);
        commitOrApply(editor);
    }

    public static void set(Context context, int resId, String value) {
        set(context, context.getString(resId), value);
    }

    public static void set(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        commitOrApply(editor);
    }

    public static void set(Context context, HashMap<String, Integer> valueMap) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        for (Map.Entry entry : valueMap.entrySet())
            editor.putInt((String) entry.getKey(), ((Integer) entry.getValue()));
        commitOrApply(editor);
    }

    public static void set(Context context, String key, Object object) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();

        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }

        if (key.equals("")) {
            throw new IllegalArgumentException("Key is empty or null");
        }

        editor.putString(key, new Gson().toJson(object));
        commitOrApply(editor);
    }

    public static void setStringMap(Context context, HashMap<String, String> valueMap) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        for (Map.Entry entry : valueMap.entrySet())
            editor.putString((String) entry.getKey(), entry.getValue().toString());
        commitOrApply(editor);
    }

    public static void setStringSetPreferences(Context context, String prefKey, Set<String>
            prefValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putStringSet(prefKey, prefValue);
        commitOrApply(editor);
    }

    public static Set<String> getStringSetPreferences(Context context, String prefKey) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        if (prefs.contains(prefKey))
            return prefs.getStringSet(prefKey, new HashSet<String>());
        else
            return new HashSet<>();

    }

    public static boolean get(Context context, int resId, boolean defValue) {
        return get(context, context.getString(resId), defValue);
    }

    public static boolean get(Context context, String key, boolean defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, defValue);
    }

    public static float get(Context context, int resId, float defValue) {
        return get(context, context.getString(resId), defValue);
    }

    public static float get(Context context, String key, float defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getFloat(key, defValue);
    }

    public static int get(Context context, int resId, int defValue) {
        return get(context, context.getString(resId), defValue);
    }

    public static int get(Context context, String key, int defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getInt(key, defValue);
    }

    public static long get(Context context, int resId, long defValue) {
        return get(context, context.getString(resId), defValue);
    }

    public static long get(Context context, String key, long defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getLong(key, defValue);
    }

    public static String get(Context context, int resId, String defValue) {
        return get(context, context.getString(resId), defValue);
    }

    public static String get(Context context, String key, String defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getString(key, defValue);
    }

    public static Editor getEditor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    private static Method findApplyMethod() {
        try {
            Class<Editor> cls = Editor.class;
            return cls.getMethod("apply");
        } catch (NoSuchMethodException unused) {
            if (DEBUG) {
                Timber.d("Failed to retrieve Editor.apply(); probably doesn't exist on this phone's OS.  Using Editor.commit() instead.");
            }
            return null;
        }
    }

    private static void commitOrApply(Editor editor) {
        if (APPLY_METHOD != null) {
            try {
                APPLY_METHOD.invoke(editor);
                return;
            } catch (InvocationTargetException | IllegalAccessException e) {
                if (DEBUG) {
                    Timber.d("Failed while using Editor.apply().  Using Editor.commit() instead.");
                }
            }
        }

        editor.commit();
    }

    public static void removeAll(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        prefs.edit().clear().apply();
    }
}
