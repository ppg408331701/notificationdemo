
package ppg.com.yanlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 简单的会话操作
 *
 * @author jie.yang
 */
public class SessionUtils {

    private static final String PREFERENCES_EDITOR_ID = "sessionID";
    public static final String SHARED_PREFERENCES_NAME = "persistence";

    /**
     * 存储会话
     *
     * @param context
     * @param session
     */
    public static void storeSession(Context context, String session) {
        if (context == null)
            return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(PREFERENCES_EDITOR_ID, session);
        editor.commit();
    }

    public static void storeData(Context context, String key, String data) {
        if (context == null)
            return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public static void storeData(Context context, String key, long data) {
        if (context == null)
            return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(key, data);
        editor.commit();
    }

    public static void storeDataInt(Context context, String key, int data) {
        if (context == null)
            return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    public static long extractDataLong(Context context, String key) {
        if (context == null)
            return 0;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    public static int extractDataInt(Context context, String key) {
        if (context == null)
            return 0;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    /**
     * 提取会话
     *
     * @param context
     * @return
     */
    public static String extractData(Context context, String key) {
        if (context == null)
            return "";
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 提取会话
     *
     * @param context
     * @return
     */
    public static String extractSession(Context context) {
        if (context == null)
            return "";
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREFERENCES_EDITOR_ID, "");
    }

    /**
     * 清空会话
     *
     * @param context
     */
    public static void clearSession(Context context) {
        if (context == null)
            return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(PREFERENCES_EDITOR_ID, "");
        editor.commit();
    }

    public static void clearData(Context context, String key) {
        if (context == null)
            return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, "");
        editor.commit();
    }

    public static void clearLongData(Context context, String key) {
        if (context == null)
            return;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(key, 0);
        editor.commit();
    }

    /**
     * 会话是否存在
     *
     * @param context
     * @return
     */
    public static boolean isExist(Context context) {
        if (context == null)
            return false;
        return TextUtils.isEmpty(extractSession(context)) ? false : true;
    }

    /**
     * 会话是否存在
     *
     * @param context
     * @return
     */
    public static boolean isExistData(Context context, String key) {
        if (context == null)
            return false;
        return TextUtils.isEmpty(extractData(context, key)) ? false : true;
    }
}
