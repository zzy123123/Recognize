package com.rubbish.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Set;

@SuppressLint("NewApi")
public class SettingUtils {
	public final static String ACCOUNT = "account";//
    public static final String UID = "userId";
    public static final String USDT = "usdt";
    public static final String PERC = "perc";
	public static final String VIRBET = "virbet";
	public static final String MUSIC = "music";
    public static final String LEVEL = "level";
	public static final String VOLUME = "volume";
    public static final String AVATAR = "avatr";
	public static final String NAME = "name";
    public static final String VERIFY = "verify";
	public static final String EMAIL = "email";
    public static String PASSWORD = "password";//
	public static String IS_LOGIN = "is_login";//
	public static String TOKEN = "token";//
	public static String LOAN_MONEY = "money";//
	public static String FIANCE__MONEY = "fiance_money";//
	public static final String DEFAULT_ROBOT = "default.rebot";
	public static boolean getPreferencesBoolean(Context context, String key,
                                                boolean defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean value = prefs.getBoolean(key, defaultValue);
		return value;
	}

	public static boolean setPreferencesBoolean(Context context, String key,
                                                boolean value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	public static String getPreferencesString(Context context, String key,
                                              String defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getString(key, defaultValue);
	}

	public static boolean setPreferencesString(Context context, String key,
                                               String value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	public static void setPreferencesInt(Context context, String key, int value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putInt(key, value).commit();
	}

	public static int getPreferencesInt(Context context, String key,
                                        int defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getInt(key, defaultValue);
	}

	public static void setPreferencesFloat(Context context, String key,
                                           float value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putFloat(key, value).commit();
	}

	public static float getPreferencesFloat(Context context, String key,
                                            float defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getFloat(key, defaultValue);
	}

	public static void setPreferencesLong(Context context, String key,
                                          long value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putLong(key, value).commit();
	}

	public static long getPreferencesLong(Context context, String key,
                                          long defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getLong(key, defaultValue);
	}

	public static void setPreferencesStringSet(Context context, String key,
                                               Set<String> value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putStringSet(key, value).commit();
	}

	public static Set<String> getPreferencesStringSet(Context context,
                                                      final String key) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getStringSet(key, null);
	}

	public static void clearPreferences(Context context, SharedPreferences p) {
		Editor editor = p.edit();
		editor.clear();
		editor.commit();
	}
}
