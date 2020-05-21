package com.kagami.mireaderlauncher;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedValue
{
	static final private String PREF_NAME = "my_preference_name";
	static private Context mContext = null;

	static public void init(Context context)
	{
		mContext = context;
	}

	static public String getString(String key)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		return pref.getString(key, null);
	}

	static public void setString(String key, String value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.apply();
	}

	static public long getLong(String key, long defaultValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		return pref.getLong(key, defaultValue);
	}

	static public void setLong(String key, long value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	static public int getInt(String key, int defaultValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		return pref.getInt(key, defaultValue);
	}

	static public void setInt(String key, int value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	static public boolean getBoolean(String key, boolean defaultValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		return pref.getBoolean(key, defaultValue);
	}

	static public void setBoolean(String key, boolean value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	static public float getFloat(String key, float defaultValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		return pref.getFloat(key, defaultValue);
	}

	static public void setFloat(String key, float value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putFloat(key, value);
		editor.apply();
	}
}
