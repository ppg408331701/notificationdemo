package com.zhy.http.okhttp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;


/**
 * 
 * @author jie.yang
 *
 */
public class JsonFileCache {
	
	public static final String SHARED_PREFERENCES_NAME = "jsonfilecache";
	
	public static void storeData(Context context, String key, String data) {
		if(context == null)
			return;
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
        editor.putString(key, data);
        editor.commit();
	}
	
	/**
	 * 提取会话
	 * @param context
	 * @return
	 */
	public static String extractData(Context context, String key) {
		if(context == null)
			return "";
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return preferences.getString(key, ""); 
	}
	
	public static void clearData(Context context, String key) {
		if(context == null)
			return;
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
        editor.putString(key, "");
        editor.commit();
	}
	
	public static void clearAll(Context context) {
		if(context == null)
			return;
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		preferences.edit().clear().commit();
	}
	
	public static boolean isExistData(Context context, String key) {
		if(context == null)
			return false;
		return TextUtils.isEmpty(extractData(context, key)) ? false: true;
	}
	
}
