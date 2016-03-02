package com.zxsong.mymall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zxsong on 2016/2/29.
 */
public class PreferenceUtils {

    public static final String PREFERENCE_NAME = "Pref_Common";

    /**
     * String类型
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putString(Context context, String key, String value) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(Context context, String key) {

        return getString(context, key, null);
    }

    private static String getString(Context context, String key, String defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    /**
     * Int类型
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putInt(Context context, String key, int value) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(Context context, String key) {

        return getInt(context, key, -1);
    }

    private static int getInt(Context context, String key, int defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }

    /**
     * Long类型
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putLong(Context context, String key, long value) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(Context context, String key) {

        return getLong(context, key, -1);
    }

    private static long getLong(Context context, String key, long defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getLong(key, defaultValue);
    }

    /**
     * Float类型
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putFloat(Context context, String key, float    value) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(Context context, String key) {

        return getFloat(context, key, -1);
    }

    private static float getFloat(Context context, String key, int defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }

    /**
     * Boolean类型
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putBoolean(Context context, String key, boolean value) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {

        return getBoolean(context, key, false);
    }

    private static boolean getBoolean(Context context, String key, boolean   defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defaultValue);
    }

}
