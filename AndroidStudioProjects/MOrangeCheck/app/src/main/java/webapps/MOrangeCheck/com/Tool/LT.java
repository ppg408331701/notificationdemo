package webapps.MOrangeCheck.com.Tool;

import android.text.TextUtils;
import android.util.Log;



import java.util.List;

import MyLogger.Logger;
import webapps.MOrangeCheck.com.Constants.Constans;

/**
 * @author itheima
 * @time 2015-6-23 上午11:33:49
 * @des 日志级别是LEVEL_ALL显示所有信息, 包括System.out.println信息
 * @des 日志级别是LEVEL_OFF关闭所有信息, 包括System.out.println信息
 * @updateAuthor TODO
 * @updateTime TODO
 * @updateDes TODO
 */
public class LT {

//	public static final int DEBUGLEVEL = LT.LEVEL_ALL;// LEVEL_ALL显示所有日志;LEVEL_OFF关闭日志显示
    //    public static final int DEBUGLEVEL = LT.LEVEL_OFF;// LEVEL_ALL显示所有日志;LEVEL_OFF关闭日志显示

    /**
     * 日志输出时的TAG
     */
    private static String mTag = "ppg";
    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_OFF = 0;
    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_ALL = 7;

    /**
     * 日志输出级别V
     */
    public static final int LEVEL_VERBOSE = 1;
    /**
     * 日志输出级别D
     */
    public static final int LEVEL_DEBUG = 2;
    /**
     * 日志输出级别I
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 日志输出级别W
     */
    public static final int LEVEL_WARN = 4;
    /**
     * 日志输出级别E
     */
    public static final int LEVEL_ERROR = 5;
    /**
     * 日志输出级别S,自定义定义的一个级别
     */
    public static final int LEVEL_SYSTEM = 6;

    /**
     * 是否允许输出log
     */
    private static int mDebuggable = Constans.DEBUGLEVEL;

    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();


    /**---------------日志输出,已固定TAG  begin---------------**/

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Logger.init(mTag).hideThreadInfo();
            Logger.e(msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void ee(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {

            Log.e(mTag,msg);
        }
    }



    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String tag, String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Logger.init(tag).hideThreadInfo();
            Logger.e(msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(Throwable msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Logger.init(mTag);
            Logger.e(msg.toString());
        }
    }


//    /**
//     * 把Log存储到文件中
//     *
//     * @param log  需要存储的日志
//     * @param path 存储路径
//     */
//    public static void log2File(String log, String path) {
//        log2File(log, path, true);
//    }
//
//    public static void log2File(String log, String path, boolean append) {
//        synchronized (mLogLock) {
//            FileUtils.writeFile(log + "\r\n", path, append);
//        }
//    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
     *
     * @param msg 需要输出的msg
     */
    public static void msgStartTime(String msg) {
        mTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            e("[Started：" + mTimestamp + "]" + msg);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg
     */
    public static void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        e("[Elapsed：" + elapsedTime + "]" + msg);
    }

    public static <T> void printList(List<T> list) {
        if (list == null || list.size() < 1) {
            return;
        }
        int size = list.size();
        Log.e("", "---begin---");
        for (int i = 0; i < size; i++) {
            Log.e("", i + ":" + list.get(i).toString());
        }
        Log.e("", "---end---");
    }

    public static <T> void printArray(T[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        int length = array.length;
        Log.e("", "---begin---");
        for (int i = 0; i < length; i++) {
            Log.e("", i + ":" + array[i].toString());
        }
        Log.e("", "---end---");
    }
}
