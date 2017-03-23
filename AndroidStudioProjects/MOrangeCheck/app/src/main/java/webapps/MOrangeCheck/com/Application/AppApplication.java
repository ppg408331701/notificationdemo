package webapps.MOrangeCheck.com.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import ppg.com.yanlibrary.utils.SessionUtils;
import utils.SPUtils;
import utils.Utils;
import webapps.MOrangeCheck.com.Tool.BoxingGlideLoader;
import webapps.MOrangeCheck.com.Tool.BoxingUcrop;


/**
 * Created by ppg on 2016/10/13.
 */

public class AppApplication extends Application {

    private static int navigationHeight;


    private static Context mContext;
    private static int statusHeight;
    private static int screenWidth;
    private static int screenHeight;
    private static Handler mHandler;
    private static Thread mMainThread;
    private static long mMainThreadId;


    private static Map<String, String> headers= new HashMap<>();
    private static SPUtils spUtils;

    public static void setHeaders(String token) {

        headers.put("Authorization", "Bearer " + token);
        SessionUtils.storeData(mContext,"Authorization","Bearer " + token);
    }

    public static void setHeadersNoBearer(String token) {
        headers.put("Authorization", token);
        SessionUtils.storeData(mContext,"Authorization",token);

    }

    public static Map<String, String> getHeaders() {
        headers.put("Authorization", SessionUtils.extractData(mContext,"Authorization"));
        return headers;
    }

    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
        LitePal.initialize(this);
        //启动服务
//        Intent intent = new Intent(this, BackgroundService.class);
//        startService(intent);

        //必须调用初始化
        OkGo.init(this);
        OkGo.getInstance()
                .debug("okgo", Level.INFO, true)
                .setCookieStore(new PersistentCookieStore()) //cookie持久化存储，如果cookie不过期，则一直有效
                .setCacheMode(CacheMode.DEFAULT)
                .setCertificates();//信任所有证书,不安全有风险

        //初始化工具类
        Utils.init(this);
        spUtils = new SPUtils(this.getPackageName());

        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());


//        InitializeService.start(this);
//        Recovery.getInstance()
//                .debug(true)
//                .recoverInBackground(false)
//                .recoverStack(true)
//                .mainPage(DetailActivity.class)
//                .callback(new RecoveryCallback() {
//                    @Override
//                    public void stackTrace(String stackTrace) {
//
//                    }
//
//                    @Override
//                    public void cause(String cause) {
//
//                    }
//
//                    @Override
//                    public void exception(String throwExceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
//
//                    }
//                })
//                .init(this);


    }

    public static Context getContext() {
        return mContext;
    }


    /**
     * save foreground Activity which registered eventlistener
     */
    private final static List<Activity> activityList = new ArrayList<Activity>();

    public static void pushActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(0, activity);
        }
    }

    public static void popActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAllActivity() {
        for (Activity activity : activityList) {
            if (activity!=null){
                if (!activity.isFinishing()) {
                    activity.finish();//退出Activity
                }
            }
        }
    }

    public static boolean hasForegroundActivies() {
        return activityList.size() != 0;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static SPUtils getSpUtils() {
        return spUtils;
    }

    // 获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // 获取状态栏高度
    public int getNavigationBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
